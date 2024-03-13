package org.jeecg.modules.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.entity.wxMessage.TextMessage;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author yh
 * @date 2020/8/21 10:19
 * @description: 事件消息业务分发器
 */
@Slf4j
public class EventDispatcher {
    @Resource
    private ISysUserService sysUserService;

    public static String processEvent(Map<String, String> map) {
        String openid = map.get("FromUserName"); //用户openid
        String mpid = map.get("ToUserName");   //公众号原始ID
        TextMessage textMessageRes = new TextMessage();
        textMessageRes.setToUserName(openid);
        textMessageRes.setFromUserName(mpid);
        textMessageRes.setCreateTime(new Date().getTime());
        textMessageRes.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { //关注事件
            log.info("==============这是关注事件！");
            String content =
                    "[Party]欢迎使用I型人群出门神器！[Party]\n" + "[Party]终于等到你！欢迎你的关注！\n" +"\n" +
                            "点击<a href=\"http://47.98.228.176:3000/wxDefault?url="+openid+"\">登录</a>\n" + "\n";
            textMessageRes.setContent(content);
            String jsonString = JSONObject.toJSONString(map);
            log.info(jsonString);
            return MessageUtil.textMessageToXml(textMessageRes);
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { //取消关注事件
            log.info("==============这是取消关注事件！");
            return "取消关注";
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)) { //扫描二维码事件
            log.info("==============这是扫描二维码事件！");
            return "这是扫描二维码事件";
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)) { //位置上报事件
            log.info("==============这是位置上报事件！");
            return "这是位置上报事件";
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { //自定义菜单点击事件
            log.info("==============这是自定义菜单点击事件！");
            return "这是自定义菜单点击事件";
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)) { //自定义菜单View事件
            log.info("==============这是自定义菜单View事件！");
            return "这是自定义菜单View事件";
        }
        return "欢迎来到I型人群出门神器";

    }




}