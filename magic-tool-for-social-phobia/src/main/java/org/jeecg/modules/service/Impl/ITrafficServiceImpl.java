package org.jeecg.modules.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.dto.EchartsDTO;
import org.jeecg.modules.entity.ITraffic;
import org.jeecg.modules.mapper.ITrafficMapper;
import org.jeecg.modules.service.ITrafficService;
import org.jeecg.modules.utils.DateUtil;
import org.jeecg.modules.vo.ITrafficChartsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.jeecg.modules.utils.DateUtil.getIntervalTimeList;
import static org.jeecg.modules.utils.DateUtil.isSameDay;

/**
 * @author 余吉钊
 * @date 2024/1/25 17:10
 */
@Service
@Slf4j
public class ITrafficServiceImpl extends ServiceImpl<ITrafficMapper, ITraffic> implements ITrafficService {

    @Resource
    private ITrafficMapper dao;

    public static String roundDownToNearest10Minutes(Date date) {
        long time = date.getTime();
        long roundedTime = (time / (10 * 60 * 1000)) * (10 * 60 * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date(roundedTime));
    }

    @Override
    public EchartsDTO<Integer> computedCharts(ITrafficChartsVO chartsVO) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        chartsVO.setMonitorSpilt("00:01:00");
        List<ITraffic> iTrafficList = dao.findEchartsList(chartsVO, loginUser);
        List<String> dayList = DateUtil.dayList(chartsVO.getBeginTime(), chartsVO.getEndTime(), "yyyy-MM-dd", 31);
        EchartsDTO<Integer> resp = new EchartsDTO<>();
        List<String> minList = getIntervalTimeList(DateUtil.beginOfDate(new Date()), DateUtil.endOfDate(new Date()),
                "HH:mm", 10);
        resp.setXAxis(minList);
        List<EchartsDTO.Series<Integer>> seriesList = new ArrayList<>();
        for (String day : dayList) {
            EchartsDTO.Series<Integer> series = new EchartsDTO.Series<>();
            series.setName(day);
            Date dateDay = sdf1.parse(day);
            Map<String, Integer> dateMap = iTrafficList.stream().filter(v -> isSameDay(v.getCreateTime(), dateDay))
                    .collect(Collectors.groupingBy(v -> roundDownToNearest10Minutes(v.getCreateTime()),
                            Collectors.summingInt(ITraffic::getTraffic)));

            List<Integer> data = new ArrayList<>();
            for (String min : minList) {
                data.add(dateMap.getOrDefault(min, 0));
            }
            series.setData(data);
            seriesList.add(series);
        }
        resp.setSeries(seriesList);
        return resp;
    }


}
