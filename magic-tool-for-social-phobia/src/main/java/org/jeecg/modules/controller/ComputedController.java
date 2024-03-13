package org.jeecg.modules.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.dto.EchartsDTO;
import org.jeecg.modules.entity.ITrafficUserConfig;
import org.jeecg.modules.entity.task.SendWxTemplateMessageTask;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.service.ITrafficService;
import org.jeecg.modules.service.ITrafficUserConfigService;
import org.jeecg.modules.task.DynamicTaskService;
import org.jeecg.modules.vo.ITrafficChartsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author 余吉钊
 * @date 2024/1/25 16:47
 */
@RestController
@RequestMapping("/computed")
@Api(tags = "首页管理-人流量数据")
@Slf4j
public class ComputedController {

    private final DynamicTaskService dynamicTask;
    @Autowired
    private ITrafficService iTrafficService;
    @Autowired
    private ITrafficUserConfigService userConfigService;

    public ComputedController(DynamicTaskService dynamicTask) {
        this.dynamicTask = dynamicTask;
    }

    @ApiOperation("人流量统计图数据")
    @PostMapping("/charts")
    public Result<EchartsDTO<Integer>> chartsData(@RequestBody @Valid ITrafficChartsVO iTrafficChartsVO) throws ParseException {
        EchartsDTO<Integer> dto = iTrafficService.computedCharts(iTrafficChartsVO);
        return Result.ok(dto);
    }

    @ApiOperation("计算出行时间")
    @GetMapping("/getTravelTime")
    public Result<EchartsDTO<Integer>> getTravelTime(@RequestParam(
            "id") @NotNull @Valid String configId) throws BusinessException {
        ITrafficUserConfig userConfig = userConfigService.getById(configId);
        if (ObjectUtil.isEmpty(userConfig)) {
            return Result.error("配置不存在");
        }
        EchartsDTO<Integer> echartsDTO = userConfigService.getBestTravelTime(userConfig);
        List<Integer> dataList = echartsDTO.getSeries().get(0).getData();
        Integer maxTraffic = dataList.stream().max(Comparator.naturalOrder()).get();
        String maxTime = echartsDTO.getXAxis().get(0);
        for (int i = 0; i < dataList.size(); i++) {
            if (i != 0 && dataList.get(i) < maxTraffic) {
                maxTraffic = dataList.get(i);
                maxTime = echartsDTO.getXAxis().get(i);
            }
        }
        if (userConfig.getState().equals(1)) {
            //计算提醒时间
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00");
            String nowDateStr = LocalDateTime.now().format(formatter2);
            LocalDateTime computedDate = LocalDateTime.parse(nowDateStr.substring(0, "yyyy-MM-dd ".length()) +
                    maxTime + ":00", formatter2);
            if (LocalDateTime.now().isAfter(computedDate)) {
                computedDate = computedDate.plusDays(1);
            }
            ZoneId zoneId = ZoneId.systemDefault();
            String taskName = "开启提醒:" + userConfig.getId();
            dynamicTask.add(new SendWxTemplateMessageTask(taskName,
                    Date.from(computedDate.atZone(zoneId).toInstant()), configId));
            log.info(taskName + ",提醒时间：" + computedDate);
        }else {
            return Result.error("提醒失败，配置未开启！");
        }
        return Result.OK("最佳出行时间是:" + maxTime + ",人数为：" + maxTraffic +
                ",PS:若多个时间段出行人数一样很少，则取第一个时间段", echartsDTO);
    }


    @ApiOperation("测试：5秒后发送模板消息")
    @GetMapping("sendMessageFiveSecond")
    public Result<String> sendMessageFiveSecond() throws BusinessException {
        Date time = DateUtil.offsetSecond(new Date(), 5);
        dynamicTask.add(new SendWxTemplateMessageTask("测试任务1", time, "1"));
        return Result.OK("发送成功");
    }
}
