package org.jeecg.modules.service;

import org.jeecg.modules.entity.Forecast;

/**
 * @author 余吉钊
 * @date 2024/2/20 17:24
 */
public interface IWeatherService {
    Forecast getWeatherByCityCode(String city);
}
