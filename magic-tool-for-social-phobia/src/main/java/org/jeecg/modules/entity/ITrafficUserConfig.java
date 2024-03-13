package org.jeecg.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 余吉钊
 * @date 2024/2/19 16:58
 */
@Data
@ApiModel("出行用户配置")
public class ITrafficUserConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("0：不开启提醒 1：开启提醒")
    private Integer state;

    @ApiModelProperty("出行开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outBeginTime;

    @ApiModelProperty("出行结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outEndTime;

    @ApiModelProperty("所属用户id")
    private String userId;

    @ApiModelProperty("所属用户名称")
    @TableField(exist = false)
    private String userName;

    @ApiModelProperty("绑定设备id")
    private String monitorId;

    @ApiModelProperty("设备备注")
    private String backup;

    @ApiModelProperty("微信关注者id")
    private String wxOpenId;

    @ApiModelProperty("城市编码")
    private String cityCode;

}
