package org.jeecg.modules.constant;

/**
 * @author 余吉钊
 * @date 2024/2/18 11:34
 */
public interface RedisKeyConstant {
    /**
     * 获取城市天气
     */
    String WEATHER="WEATHER:";
    /**
     * 城市编码
     */
    String CITY_CODE="CITY_CODE";


    //    ----------------------redis lock--------------------------------
    /**
     * 添加任务
     */
    String TASK_ADD = "TASK_ADD:";
    /**
     * 删除任务
     */
    String TASK_DEL = "TASK_DEL";
}
