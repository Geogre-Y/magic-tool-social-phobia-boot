package org.jeecg.modules.message.handle.impl;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.message.handle.ISendMsgHandle;

/**
 * @Description: 发微信消息模板
 * @Author 余维华
 */
@Slf4j
public class WxSendMsgHandle implements ISendMsgHandle {

	@Override
	public void sendMsg(String esReceiver, String esTitle, String esContent) {
		// TODO-Jeecg Source Auto-generated method stub
		log.info("发微信消息模板");
	}

}
