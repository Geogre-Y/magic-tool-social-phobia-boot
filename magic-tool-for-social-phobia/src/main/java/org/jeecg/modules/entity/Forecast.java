package org.jeecg.modules.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 余吉钊
 * @date 2024/2/19 11:57
 */
@Data
public class Forecast {
    @ApiModelProperty("城市编码")
    private String adcode;
    private List<Cast> casts;
    @ApiModelProperty("城市")
    private String city;
    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("预报发布时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reporttime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("每天更新时间")
    private Date expiryTime;
}
