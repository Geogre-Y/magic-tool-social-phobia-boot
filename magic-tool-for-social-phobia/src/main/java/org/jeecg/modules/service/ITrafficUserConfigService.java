package org.jeecg.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.dto.EchartsDTO;
import org.jeecg.modules.entity.ITrafficUserConfig;
import org.jeecg.modules.entity.task.SendWxTemplateMessageTask;

import java.util.List;

/**
 * @author 余吉钊
 * @date 2024/1/25 16:50
 */
public interface ITrafficUserConfigService extends IService<ITrafficUserConfig> {

    /**
     * 获取最佳出行时间
     * @return
     */
    EchartsDTO<Integer> getBestTravelTime(ITrafficUserConfig userConfig);


}
