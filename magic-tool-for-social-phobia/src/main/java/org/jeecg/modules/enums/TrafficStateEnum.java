package org.jeecg.modules.enums;

/**
 * @author 余吉钊
 * @date 2024/2/19 14:51
 */
public enum TrafficStateEnum {

    All("开启",1),
    BASE("关闭",0);

    private String name;
    private Integer value;

    // 构造方法
    private TrafficStateEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
