package org.jeecg.modules.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 余吉钊
 * @date 2024/2/19 15:00
 */
@Configuration
public class FeignLogConfig {
    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
