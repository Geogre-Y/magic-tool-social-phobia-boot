package org.jeecg.modules.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.constant.RedisKeyConstant;
import org.jeecg.modules.dto.resp.CityCodeResp;
import org.jeecg.modules.entity.CityCode;
import org.jeecg.modules.entity.Forecast;
import org.jeecg.modules.service.ICityCodeService;
import org.jeecg.modules.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 余吉钊
 * @date 2024/2/19 11:44
 */
@RestController
@RequestMapping("weather")
@Api(tags = "天气信息")
@Slf4j
public class WeatherController {
    @Autowired
    private IWeatherService weatherService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private ICityCodeService cityCodeService;
    private Boolean con;

    /**
     * @param city 城市编码
     * @return
     */
    @GetMapping("getWeather")
    @ApiOperation("获取天气信息")
    Result<Forecast> getWeather(@RequestParam("city") String city) {
        return Result.OK(weatherService.getWeatherByCityCode(city));
    }

    @GetMapping("getCityCode")
    @ApiOperation("获取城市编码")
    Result<List<CityCodeResp>> getCityCode() {
        String jsonString = redisTemplate.opsForValue().get(RedisKeyConstant.CITY_CODE);
        List<CityCodeResp> resp = new ArrayList<>();
        if (StringUtils.isEmpty(jsonString)) {
            List<CityCode> cityCodes = cityCodeService.list();
            for (int i = 0; i < cityCodes.size(); i++) {
                CityCodeResp cityCodeResp = new CityCodeResp();
                BeanUtil.copyProperties(cityCodes.get(i), cityCodeResp);
                if (i == 0) {
                    resp.add(cityCodeResp);
                    continue;
                }
                CityCodeResp lastCity = new CityCodeResp();
                BeanUtil.copyProperties(cityCodes.get(i - 1), lastCity);
                setCity(resp, cityCodeResp, lastCity, 0);
            }
            redisTemplate.opsForValue().set(RedisKeyConstant.CITY_CODE, JSONArray.toJSONString(resp));
        } else {
            resp = JSONArray.parseArray(jsonString, CityCodeResp.class);
        }
        return Result.ok(resp);
    }

    private void setCity(List<CityCodeResp> resp, CityCodeResp city, CityCodeResp lastCity, int index) {
        if (index > 4) {
            return;
        }
        String sub = city.getAdcCode().substring(index, index + 2);
        String lastSub = lastCity.getAdcCode().substring(index, index + 2);
//        判断是否是直辖市 PS：直辖市只有二级
        if (index == 0 && city.getAdcCode().equals(city.getCityCode())) {
            resp.add(city);
            con = true;
            return;
        }
//        是直辖市下属的城市，只要到二级children
        if (index == 2 && con) {
            CityCodeResp second = resp.get(resp.size() - 1);
            System.out.println(second.getCityName());
            if (CollectionUtil.isEmpty(second.getChildren())) {
                List<CityCodeResp> cityCodeResps = new ArrayList<>();
                cityCodeResps.add(city);
                second.setChildren(cityCodeResps);
            } else {
                second.getChildren().add(city);
            }
            return;
        }
//        否则递归
        if (sub.equals(lastSub)) {
            setCity(resp, city, lastCity, index + 2);
        } else {
//            0 2 4 分别代表1 2 3 级
            switch (index) {
                case 0:
                    resp.add(city);
                    break;
                case 2:
                    CityCodeResp second = resp.get(resp.size() - 1);
                    if (CollectionUtil.isEmpty(second.getChildren())) {
                        List<CityCodeResp> cityCodeResps = new ArrayList<>();
                        cityCodeResps.add(city);
                        second.setChildren(cityCodeResps);
                    } else {
                        second.getChildren().add(city);
                    }
                    break;
                case 4:
                    List<CityCodeResp> sencondList = resp.get(resp.size() - 1).getChildren();
                    if (CollectionUtil.isEmpty(sencondList)) {
                        break;
                    }
                    CityCodeResp third = sencondList.get(sencondList.size() - 1);
                    if (CollectionUtil.isEmpty(third.getChildren())) {
                        List<CityCodeResp> cityCodeResps = new ArrayList<>();
                        cityCodeResps.add(city);
                        third.setChildren(cityCodeResps);
                    } else {
                        third.getChildren().add(city);

                    }
                    break;
            }

        }
//        不是直辖市，则正常走1 2 3 级
        if (!city.getAdcCode().equals(city.getCityCode()) && city.getAdcCode().startsWith("0000", 2)) {
            con = false;
        }
    }
}
