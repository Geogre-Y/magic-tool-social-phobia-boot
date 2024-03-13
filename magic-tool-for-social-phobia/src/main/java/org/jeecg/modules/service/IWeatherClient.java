package org.jeecg.modules.service;

import org.jeecg.modules.dto.GDWeatherDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 余吉钊
 * @date 2024/2/19 11:45
 */
@FeignClient(name = "weather", url = "https://restapi.amap.com/v3")
public interface IWeatherClient {

    /**
     * 请求天气预报
     *
     * @param key        高德应用key
     * @param city       城市编码
     * @param extensions 可选值：base/all  base:返回实况天气 all:返回预报天气
     * @return
     */
    @GetMapping("/weather/weatherInfo")
    GDWeatherDTO getWeather(@RequestParam(value = "key") String key,
                            @RequestParam("city") String city,
                            @RequestParam("extensions") String extensions
                           );

}
