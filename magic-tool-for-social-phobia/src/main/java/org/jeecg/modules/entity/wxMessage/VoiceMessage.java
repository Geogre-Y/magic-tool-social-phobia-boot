package org.jeecg.modules.entity.wxMessage;

import lombok.Data;

/**
 * @author 余吉钊
 * @date 2024/3/1 14:41
 */
@Data
public class VoiceMessage extends BaseMessage {
    // 语音消息媒体id，可以调用获取临时素材+接口拉取数据。
    private String MediaId;
    // 语音格式
    private String Format;
}
