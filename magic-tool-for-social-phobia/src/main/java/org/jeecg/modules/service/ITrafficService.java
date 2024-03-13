package org.jeecg.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.dto.EchartsDTO;
import org.jeecg.modules.entity.ITraffic;
import org.jeecg.modules.vo.ITrafficChartsVO;

import java.text.ParseException;
import java.util.List;

/**
 * @author 余吉钊
 * @date 2024/1/25 16:50
 */
public interface ITrafficService extends IService<ITraffic> {

    EchartsDTO<Integer> computedCharts(ITrafficChartsVO chartsVO) throws ParseException;


}
