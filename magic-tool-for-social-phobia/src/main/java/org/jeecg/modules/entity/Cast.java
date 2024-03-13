package org.jeecg.modules.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 余吉钊
 * @date 2024/2/19 11:47
 */
@Data
@ApiModel("高德天气返回类")
public class Cast {
    @ApiModelProperty("日期")
    private String date;
    @ApiModelProperty("白天风力")
    private String daypower;
    @ApiModelProperty("白天温度")
    private String daytemp;
    @ApiModelProperty("")
    private String daytempFloat;
    @ApiModelProperty("白天天气现象")
    private String dayweather;
    @ApiModelProperty("白天风向")
    private String daywind;
    @ApiModelProperty("晚上风力")
    private String nightpower;
    @ApiModelProperty("晚上温度")
    private String nighttemp;
    @ApiModelProperty("")
    private String nighttempFloat;
    @ApiModelProperty("晚上天气现象")
    private String nightweather;
    @ApiModelProperty("晚上风向")
    private String nightwind;
    @ApiModelProperty("星期几")
    private String week;


}
