package org.jeecg.modules.task;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.entity.ITraffic;
import org.jeecg.modules.entity.ITrafficUserConfig;
import org.jeecg.modules.service.ITrafficService;
import org.jeecg.modules.service.ITrafficUserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 余吉钊
 * @date 2024/1/18 15:56
 */
@Repository
@Slf4j
public class CollectionTask {

    @Autowired
    private ITrafficUserConfigService userConfigService;

    @Autowired
    private ITrafficService iTrafficService;


//    @Scheduled(cron = "0 0 01 * * ?")   //每天凌晨1点执行一次
    @Async("ComputedAsyncTask")
    public void beginCollection() {


    }

}
