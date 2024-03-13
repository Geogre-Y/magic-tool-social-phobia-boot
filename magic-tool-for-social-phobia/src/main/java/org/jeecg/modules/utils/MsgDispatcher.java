package org.jeecg.modules.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.entity.wxMessage.TextMessage;
import org.jeecg.modules.utils.MessageUtil;

import java.util.Date;
import java.util.Map;

/**
 * @author yh
 * @date 2020/8/21 10:19
 * @description: 微信消息业务处理分发器
 */
@Slf4j
public class MsgDispatcher {
    public static String processMessage(Map<String, String> map) {
        String openid=map.get("FromUserName"); //用户openid
        String mpid=map.get("ToUserName");   //公众号原始ID
        TextMessage textMessageRes = new TextMessage();
        textMessageRes.setToUserName(openid);
        textMessageRes.setFromUserName(mpid);
        textMessageRes.setCreateTime(new Date().getTime());
        textMessageRes.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
            log.info("==============这是文本消息！");
            String jsonString = JSONObject.toJSONString(map);
            log.info(jsonString);
            return MessageUtil.textMessageToXml(textMessageRes);
        }

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
            log.info("==============这是图片消息！");
            String jsonString = JSONObject.toJSONString(map);
            log.info(jsonString);
        }


        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) { // 链接消息
            log.info("==============这是链接消息！");
            String jsonString = JSONObject.toJSONString(map);
            log.info(jsonString);

        }


        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) { // 位置消息
            log.info("==============这是位置消息！");
            String jsonString = JSONObject.toJSONString(map);
            log.info(jsonString);
        }


        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) { // 视频/小视频消息
            log.info("==============这是视频消息！");
            String jsonString = JSONObject.toJSONString(map);
            log.info(jsonString);
        }

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) { // 语音消息
            log.info("==============这是语音消息！");
            String jsonString = JSONObject.toJSONString(map);
            log.info(jsonString);
        }
        return "";
    }
}
 