//package org.jeecg.modules.config;
//
//import org.jeecg.modules.constant.RabbitConstant;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.CustomExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * RabbitMQ配置
// *
// * @author 余维华
// * @date 2024/2/14 10:40
// */
//@Configuration
//public class RabbitConfig {
//
//    @Autowired
//    private RabbitAdmin rabbitAdmin;
//
//    /**
//     * 延时队列（通过延时插件实现）
//     *
//     * @return
//     */
//    @Bean("delayedQueue")
//    public Queue delayedQueue() {
//        Queue queue = new Queue(RabbitConstant.Queues.delayedQueue, true);
//        //显式声明邮件队列
//        return queue;
//
//    }
//
//    /**
//     * 延时交换机Direct交换机 起名：（通过延时插件实现）
//     * 定义了一个x-delayed-message类型的交换机，由于Spring AMQP中没有这个类型的交换机，
//     * 所以我们使用一个CustomExchange来定义这个插件构建的交换机，
//     * 它和其它交换机相同，实现了AbstructExchange。
//     * 唯一的区别是没有指定type类型。type类型可以自定义，
//     * 这样我们就可以通过构造方法自定义交换机的类型。
//     * 在使用到延迟交换机插件的时候，我们使用插件新添加了一个x-delayed-message类型的交换机。
//     *
//     * @return
//     */
//    @Bean("delayedExchange")
//    public CustomExchange delayedExchange() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("x-delayed-type", "direct");
//        return new CustomExchange(RabbitConstant.Exchanges.delayedExchange,
//                "x-delayed-message", true, false, map);
//    }
//
//    /**
//     * 绑定延时队列和延时交换机（延时插件实现方式）
//     *
//     * @param delayedQueue
//     * @param delayedExchange
//     * @return
//     */
//    @Bean
//    public Binding delayedQueue_delayedExchange(
//            @Qualifier("delayedQueue") Queue delayedQueue,
//            @Qualifier("delayedExchange") CustomExchange delayedExchange) {
//        return BindingBuilder.bind(delayedQueue)
//                .to(delayedExchange)
//                .with(RabbitConstant.RouterKey.delayedRouteKey)
//                .noargs();
//    }
//
//
//}
