package org.jeecg.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.entity.ITraffic;
import org.jeecg.modules.entity.task.SendWxTemplateMessageTask;
import org.jeecg.modules.vo.ITrafficChartsVO;

import java.util.Date;
import java.util.List;

/**
 * @author 余吉钊
 * @date 2024/1/18 16:41
 */
public interface ITrafficMapper extends BaseMapper<ITraffic> {


    /**
     * @param vo
     * @param loginUser
     * @return
     */
    List<ITraffic> findEchartsList(
            @Param("vo") ITrafficChartsVO vo,
            @Param("loginUser") LoginUser loginUser
                                  );

    /**
     * 查找给定时间段的人流量数据
     * @return
     */
    List<ITraffic> getITrafficListByCreateTime(@Param("userId") String userId,
                                               @Param("outBeginTime")Date outBeginTime,
                                               @Param("outEndTime")Date outEndTime,
                                               @Param("monitorId")String monitorId
                                               );


}
