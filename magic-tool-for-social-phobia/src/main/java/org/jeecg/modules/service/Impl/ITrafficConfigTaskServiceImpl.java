package org.jeecg.modules.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.entity.task.SendWxTemplateMessageTask;
import org.jeecg.modules.mapper.ITrafficConfigTaskMapper;
import org.jeecg.modules.service.ITrafficConfigTaskService;
import org.springframework.stereotype.Service;

/**
 * @author 余吉钊
 * @date 2024/3/8 9:38
 */
@Service
public class ITrafficConfigTaskServiceImpl extends ServiceImpl<ITrafficConfigTaskMapper, SendWxTemplateMessageTask> implements ITrafficConfigTaskService {

}
