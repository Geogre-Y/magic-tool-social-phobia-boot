package org.jeecg.modules.entity.wxMessage;

import lombok.Data;

@Data
public class ImageMessage extends BaseMessage {
    // 图片链接
    private String PicUrl;
    //图片消息媒体id，可以调用获取临时素材接口拉取数据。
    private String MediaId;
}
 