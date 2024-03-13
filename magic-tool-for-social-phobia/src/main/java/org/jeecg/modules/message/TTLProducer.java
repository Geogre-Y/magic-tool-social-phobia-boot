//package org.jeecg.modules.message;
//
//import org.jeecg.modules.constant.RabbitConstant;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * 延时消息生产者
// *
// * @author ZhengNC
// * @date 2020/9/21 14:15
// */
//@Service
//public class TTLProducer {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    /**
//     * 发送一条延时消息（延时插件的实现方式）
//     *
//     * @param message
//     */
//    public void sendDelayedMessage(String message) {
//        rabbitTemplate.convertAndSend(
//                RabbitConstant.Exchanges.delayedExchange,
//                RabbitConstant.RouterKey.delayedRouteKey,
//                message,
//                msg -> {
//                    //设置此消息延时十秒
//                    msg.getMessageProperties()
//                            .setHeader("x-delay", 10000);
//                    return msg;
//                });
//    }
//}
