package org.jeecg.modules.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 余吉钊
 * @date 2024/1/25 17:07
 */
@Data
@ApiModel("人流量折线图请求")
public class ITrafficChartsVO {

    @ApiModelProperty("开始时间")
    @NotNull
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    @ApiModelProperty("结束时间")
    @NotNull
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty("间隔（单位：分钟）")
    private String monitorSpilt;

}
