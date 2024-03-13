package org.jeecg.modules.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeecg.weibo.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.dto.EchartsDTO;
import org.jeecg.modules.entity.ITraffic;
import org.jeecg.modules.entity.ITrafficUserConfig;
import org.jeecg.modules.entity.task.SendWxTemplateMessageTask;
import org.jeecg.modules.mapper.ITrafficMapper;
import org.jeecg.modules.mapper.ITrafficUserConfigMapper;
import org.jeecg.modules.service.ITrafficService;
import org.jeecg.modules.service.ITrafficUserConfigService;
import org.jeecg.modules.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.jeecg.modules.service.Impl.ITrafficServiceImpl.roundDownToNearest10Minutes;
import static org.jeecg.modules.utils.DateUtil.getIntervalTimeList;
import static org.jeecg.modules.utils.DateUtil.isSameDay;

/**
 * @author 余吉钊
 * @date 2024/1/25 17:10
 */
@Service
@Slf4j
public class ITrafficUserConfigServiceImpl extends ServiceImpl<ITrafficUserConfigMapper,ITrafficUserConfig> implements ITrafficUserConfigService{



    @Resource
    private ITrafficMapper iTrafficMapper;

    @Override
    public EchartsDTO<Integer> getBestTravelTime(ITrafficUserConfig  userConfig) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<ITraffic> iTrafficList = iTrafficMapper.getITrafficListByCreateTime(sysUser.getId(),
                userConfig.getOutBeginTime(),userConfig.getOutEndTime(),userConfig.getMonitorId());
        if (CollectionUtil.isEmpty(iTrafficList)){
            throw new BusinessException("暂无之前数据，无法判断");
        }
        List<String> minList = getIntervalTimeList(userConfig.getOutBeginTime(), userConfig.getOutEndTime(),
                "HH:mm", 10);
        EchartsDTO<Integer> resp = new EchartsDTO<>();
        resp.setXAxis(minList);
        EchartsDTO.Series<Integer> series = new EchartsDTO.Series<>();
        List<Integer> data = new ArrayList<>();
        Map<String, Integer> dateMap = iTrafficList.stream()
                .collect(Collectors.groupingBy(v -> roundDownToNearest10Minutes(v.getCreateTime()),
                        Collectors.summingInt(ITraffic::getTraffic)));
        series.setName("筛选的时间段人流量");
        for (String min : minList) {
            data.add(dateMap.getOrDefault(min, 0));
        }
        series.setData(data);
        List<EchartsDTO.Series<Integer>> seriesList = new ArrayList<>();
        seriesList.add(series);
        resp.setSeries(seriesList);
        return resp;
    }

}
