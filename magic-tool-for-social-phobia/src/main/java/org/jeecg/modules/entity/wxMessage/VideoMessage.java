package org.jeecg.modules.entity.wxMessage;

import lombok.Data;

/**
 * @author 余吉钊
 * @date 2024/3/1 14:42
 */
@Data
public class VideoMessage extends BaseMessage{

    // 视频消息媒体id，可以调用多媒体文件下载接口拉取数据
    private String MediaId;
    // 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
    private String ThumbMediaId;

}