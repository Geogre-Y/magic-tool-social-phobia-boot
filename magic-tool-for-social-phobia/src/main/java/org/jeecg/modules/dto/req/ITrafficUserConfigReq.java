package org.jeecg.modules.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 余吉钊
 * @date 2024/2/19 17:16
 */
@Data
@ApiModel("人流量设置")
public class ITrafficUserConfigReq {
    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("0：不开启提醒 1：开启提醒")
    private Integer state;

    @ApiModelProperty("人流量监控开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outBeginTime;

    @ApiModelProperty("人流量监控结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outEndTime;

    @ApiModelProperty("监控设备")
    private String monitorId;

    @ApiModelProperty("备注")
    private String backup;

    @ApiModelProperty("城市编码")
    private String cityCode;


}
