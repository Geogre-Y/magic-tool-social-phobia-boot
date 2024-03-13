package org.jeecg.modules.config;

import feign.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


/**
 * @Description
 * @ClassName SdkAutoConfig
 * @Author bo
 * @date 2023.12.19 11:17
 */
@EnableConfigurationProperties()
public class SdkAutoConfig {

    /**
     * feign 日志级别
     * NONE 不记录任何日志
     * BASIC 仅记录请求方法、URL和响应状态码和执行时间
     * HEADERS 记录BASIC级别的基础上，记录请求和响应的header
     * FULL 记录所有日志
     *
     * @return
     */
    @ConditionalOnMissingBean
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
