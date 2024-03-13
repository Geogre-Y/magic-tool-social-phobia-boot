package org.jeecg.modules.enums;

/**
 * @author 余吉钊
 * @date 2024/2/19 14:51
 */
public enum WxTemplateEnum {

    OUT("可以出门啦-模板","ekJ4SFzLiEg8aEqN7e_KAVFW3AXxncReKXH4YutPsh4");

    private String name;
    private String value;

    // 构造方法
    private WxTemplateEnum(String name, String value) {
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
