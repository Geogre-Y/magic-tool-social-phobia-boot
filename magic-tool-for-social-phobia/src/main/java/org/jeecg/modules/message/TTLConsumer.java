//package org.jeecg.modules.message;
//
//import org.jeecg.modules.constant.RabbitConstant;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//
///**
// * 延时消息的消费者
// *
// * @author ZhengNC
// * @date 2020/9/21 14:08
// */
//@Component
//public class TTLConsumer {
//    /**
//     * 消费延时消息（延时插件实现）
//     *
//     * @param message
//     */
//    @RabbitListener(queues = RabbitConstant.Queues.delayedQueue)
//    public void delayedConsumer(String message) {
//        System.out.println("消费了一条消息，消费时间："
//                + DateTimeFormatter.ofPattern("HH:mm:ss")
//                .format(LocalTime.now()));
//        System.out.println(message);
//    }
//}
