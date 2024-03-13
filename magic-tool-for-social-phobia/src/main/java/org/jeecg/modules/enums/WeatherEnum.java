package org.jeecg.modules.enums;

import lombok.Data;

/**
 * @author 余吉钊
 * @date 2024/2/19 14:51
 */
public enum WeatherEnum {

    All("返回预报天气","all"),
    BASE("返回实况天气","base");

    private String name;
    private String value;

    // 构造方法
    private WeatherEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
