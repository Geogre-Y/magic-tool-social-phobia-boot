package org.jeecg.modules.constant;

/**
 * RabbitMQ常量
 *
 * @author 余维华
 */
public interface RabbitConstant {
    /**
     * 交换机
     */
    interface Exchanges {
        /**
         * 延时交换机（通过延时插件实现 rabbitmq_delayed_message_exchange）
         */
        String delayedExchange = "delayed";
    }

    /**
     * 队列
     */
    interface Queues {
        /**
         * 延时队列（通过延时插件实现）
         */
        String delayedQueue = "spring.boot.delayed.queue";
    }

    /**
     * 路由key
     */
    interface RouterKey {
        /**
         * 延时路由key（通过延时插件实现）
         */
        String delayedRouteKey = "delayed.route.key";
    }
}
