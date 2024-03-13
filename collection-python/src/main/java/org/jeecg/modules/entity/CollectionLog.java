package org.jeecg.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @author 余吉钊
 * @date 2024/2/18 11:01
 */
@Data
@TableName("collection_log")
public class CollectionLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * log内容
     */
    private java.lang.String content;

    /**
     * 创建人
     */
    private java.lang.String createBy;

    /**
     * 创建人头像
     */
    private java.lang.String createAvatar;


    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;


    public CollectionLog(String content) {
        this.content = content;
    }

    public CollectionLog(String content, String createAvatar) {
        this.content = content;
        this.createAvatar = createAvatar;
    }


    public CollectionLog() {
    }
}
