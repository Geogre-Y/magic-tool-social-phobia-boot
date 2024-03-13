package org.jeecg.modules.entity.wxMessage;

import lombok.Data;

@Data
public class TextMessage extends BaseMessage{
    // 消息内容
    private String Content;
}