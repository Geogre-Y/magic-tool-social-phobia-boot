package org.jeecg.modules.entity.wxMessage;

import lombok.Data;

/**
 *
 */
@Data
public class BaseMessage {
    // 开发者微信号
    private String ToUserName;
    // 发送方帐号（一个OpenID）
    private String FromUserName;
    // 消息创建时间 （整型）
    private Long CreateTime;
    // 消息类型（链接-link /地理位置-location /小视频-shortvideo/视频-video /语音-voice /图片-image /文本-text）
    private String MsgType;
    // 消息id，64位整型
    private Long MsgId;

}