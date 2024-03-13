package org.jeecg.modules.entity.task;

import cn.hutool.core.convert.ConverterRegistry;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: crush
 * @Date: 2021-07-29 15:35
 * version 1.0
 */
@Data
@Accessors(chain = true) // 方便链式编写 习惯所然
@AllArgsConstructor
@NoArgsConstructor
@TableName("i_traffic_monitor_config_task")
public class SendWxTemplateMessageTask {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 动态任务名曾
     */
    private String name;

    /**
     * 设定动态任务开始时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start;

    /**
     * 用户配置Id
     */
    private String userConfigId;

    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    public SendWxTemplateMessageTask(String name, Date start, String userConfigId) {
        this.name = name;
        this.start = start;
        this.userConfigId = userConfigId;
    }

}
