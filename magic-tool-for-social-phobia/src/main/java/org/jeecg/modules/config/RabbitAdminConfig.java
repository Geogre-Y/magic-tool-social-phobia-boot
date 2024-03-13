//package org.jeecg.modules.config;
//
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author 余吉钊
// * @date 2024/2/19 9:48
// */
//@Configuration
//public class RabbitAdminConfig {
//
//    //配置连接工厂
//    @Value("${spring.rabbitmq.addresses}")
//    private String host;
//    @Value("${spring.rabbitmq.port}")
//    private int port;
//    @Value("${spring.rabbitmq.username}")
//    private String username;
//    @Value("${spring.rabbitmq.password}")
//    private String password;
//    @Value("${spring.rabbitmq.virtual-host}")
//    private String virtualHost;
//
//    @Bean
//    public CachingConnectionFactory cachingConnectionFactory() {
//        CachingConnectionFactory cach = new CachingConnectionFactory();
//        cach.setHost(host);
//        cach.setPort(port);
//        cach.setUsername(username);
//        cach.setPassword(password);
//        cach.setVirtualHost(virtualHost);
//        return cach;
//    }
//
//    @Bean
//    public RabbitAdmin rabbitAdmin() {
//        //需要传入
//        RabbitAdmin rabbitAdmin = new RabbitAdmin(cachingConnectionFactory());
//        rabbitAdmin.setAutoStartup(true);
//        return rabbitAdmin;
//    }
//}
