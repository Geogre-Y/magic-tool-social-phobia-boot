package org.jeecg.modules.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.jeecg.weibo.exception.BusinessException;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.constant.RedisKeyConstant;
import org.jeecg.modules.dto.GDWeatherDTO;
import org.jeecg.modules.entity.Forecast;
import org.jeecg.modules.enums.WeatherEnum;
import org.jeecg.modules.service.IWeatherClient;
import org.jeecg.modules.service.IWeatherService;
import org.jeecg.modules.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 余吉钊
 * @date 2024/2/20 17:25
 */
@Service
public class IWeatherServiceImpl implements IWeatherService {
    @Value("${GD.key}")
    private String key;

    @Resource
    private IWeatherClient weatherService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 获取城市天气
     * @param city
     * @return
     */
    @Override
    public Forecast getWeatherByCityCode(String city) {
        JSONObject jsonObject = (JSONObject) redisTemplate.opsForValue().get(RedisKeyConstant.WEATHER+city);
        Forecast forecast =JSONObject.parseObject(String.valueOf(jsonObject),Forecast.class);
        if (ObjectUtil.isNotNull(forecast) && forecast.getExpiryTime().compareTo(new Date()) < 0) {
            return forecast;
        }
        List<String> cityList= Arrays.asList(city.split(","));
        city = cityList.get(cityList.size() - 1);
        GDWeatherDTO dto = weatherService.getWeather(key, city, WeatherEnum.All.getValue());
        if (dto.getInfocode().equals("10000")) {
            forecast = dto.getForecasts().get(0);
            //每天凌晨3点更新
            forecast.setExpiryTime(DateUtil.addHour(DateUtil.endOfDate(new Date()),3));
            redisTemplate.opsForValue().set(RedisKeyConstant.WEATHER+city, JSONObject.toJSON(forecast));
            return forecast;
        }
        throw new BusinessException("天气查询失败");
    }
}
