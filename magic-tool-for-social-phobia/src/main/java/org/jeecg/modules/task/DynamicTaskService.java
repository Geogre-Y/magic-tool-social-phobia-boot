package org.jeecg.modules.task;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.jeecg.modules.constant.RedisKeyConstant;
import org.jeecg.modules.entity.Cast;
import org.jeecg.modules.entity.Forecast;
import org.jeecg.modules.entity.ITrafficUserConfig;
import org.jeecg.modules.entity.task.SendWxTemplateMessageTask;
import org.jeecg.modules.enums.WxTemplateEnum;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.service.IRedisLockService;
import org.jeecg.modules.service.ITrafficConfigTaskService;
import org.jeecg.modules.service.ITrafficUserConfigService;
import org.jeecg.modules.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@DependsOn("ITrafficUserConfigServiceImpl")
@Component
@Slf4j
public class DynamicTaskService {
    private final ThreadPoolTaskScheduler syncScheduler;
    /**
     * 以下两个都是线程安全的集合类。
     */
    public Map<String, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>();
    /**
     * -- GETTER --
     * 查看已开启但还未执行的动态任务
     *
     * @return
     */
    @Getter
    public List<String> taskList = new CopyOnWriteArrayList<String>();
    @Autowired
    private WxMpService mpService;
    @Autowired
    private ITrafficConfigTaskService taskService;
    @Autowired
    private ITrafficUserConfigService userConfigService;
    @Autowired
    private IWeatherService iWeatherService;
    @Resource
    private IRedisLockService redisLockService;


    public DynamicTaskService(ThreadPoolTaskScheduler syncScheduler) {
        this.syncScheduler = syncScheduler;
    }

    @PostConstruct
    public void initTaskList() {
        List<SendWxTemplateMessageTask> taskList1 = taskService.list();
        List<SendWxTemplateMessageTask> expireTaskList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(taskList1)) {
            taskList1.forEach(v -> {
                if (v.getStart().compareTo(new Date()) > 0) {
                    ScheduledFuture<?> schedule = syncScheduler.schedule(getRunnable(v), v.getStart());
                    taskMap.put(v.getName(), schedule);
                    taskList.add(v.getName());
                } else {
                    expireTaskList.add(v);
                }
            });
        }
        if (CollectionUtil.isNotEmpty(expireTaskList)) {
//            清理过期任务
            taskService.removeByIds(expireTaskList);
        }

        log.info("实例化任务,{}", taskList1);
    }

    /**
     * 添加一个动态任务
     *
     * @param task
     * @return
     */
    public boolean add(SendWxTemplateMessageTask task) throws BusinessException {
        // 此处的逻辑是 ，如果当前已经有这个名字的任务存在，先删除之前的，再添加现在的。（即重复就覆盖）
        boolean lock = redisLockService.tryLock(RedisKeyConstant.TASK_ADD, 6000, 60, TimeUnit.SECONDS);
        if (!lock) {
            throw new BusinessException("添加任务失败，有其他任务正在添加中。。。。");
        }
        try {
            if (null != taskMap.get(task.getName())) {
                stop(task.getName());
            }
            ScheduledFuture<?> schedule = syncScheduler.schedule(getRunnable(task), task.getStart());
            taskMap.put(task.getName(), schedule);
            taskList.add(task.getName());
            taskService.save(task);
        } finally {
            redisLockService.unLock(RedisKeyConstant.TASK_ADD);
        }
        return true;
    }


    /**
     * 运行任务
     *
     * @param task
     * @return
     */
    public Runnable getRunnable(SendWxTemplateMessageTask task) {
        return () -> {
            log.info("---动态定时发送Wx模板消息---");
            try {
                sendTemplateMessage(task.getUserConfigId());
            } catch (WxErrorException e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * 停止任务
     *
     * @param name
     * @return
     */
    public boolean stop(String name) throws BusinessException {
        boolean lock = redisLockService.tryLock(RedisKeyConstant.TASK_DEL, 6000, 60, TimeUnit.SECONDS);
        if (!lock) {
            throw new BusinessException("删除任务失败，有其他锁添加中。。。。");
        }
        try {
            if (null == taskMap.get(name)) {
                return false;
            }
            ScheduledFuture<?> scheduledFuture = taskMap.get(name);
            scheduledFuture.cancel(true);
            taskMap.remove(name);
            taskList.remove(name);
            QueryWrapper<SendWxTemplateMessageTask> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", name);
            taskService.remove(queryWrapper);
        } finally {
            redisLockService.unLock(RedisKeyConstant.TASK_DEL);
        }
        return true;
    }

    public void sendTemplateMessage(String userConfigId) throws WxErrorException {
        ITrafficUserConfig userConfig = userConfigService.getById(userConfigId);
        if (ObjectUtil.isNull(userConfig)) {
            log.error("发送模板消息失败，配置不存在");
            return;
        }
        WxMpTemplateMessage message = new WxMpTemplateMessage();
        message.setToUser(userConfig.getWxOpenId());
        message.setTemplateId(WxTemplateEnum.OUT.getValue());
        //点击模板跳转URL
        message.setUrl("http://47.98.228.176:3000/wxDefault?url=" + userConfig.getWxOpenId());

        //设置模板数据
        List<WxMpTemplateData> dataList = new ArrayList<>();
        WxMpTemplateData data = new WxMpTemplateData();
        data.setName("outTime");
        data.setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        dataList.add(data);
        Forecast forecast = iWeatherService.getWeatherByCityCode(userConfig.getCityCode());
        Cast cast = forecast.getCasts().get(0);

        WxMpTemplateData data1 = new WxMpTemplateData();
        data1.setName("city");
        data1.setValue(forecast.getCity());
        dataList.add(data1);

        WxMpTemplateData data2 = new WxMpTemplateData();
        data2.setName("dayweather");
        data2.setValue(cast.getDayweather());
        dataList.add(data2);

        WxMpTemplateData data3 = new WxMpTemplateData();
        data3.setName("daytemp");
        data3.setValue(cast.getDaytemp() + "°C");
        dataList.add(data3);

        WxMpTemplateData data4 = new WxMpTemplateData();
        data4.setName("nightweather");
        data4.setValue(cast.getNightweather());
        dataList.add(data4);

        WxMpTemplateData data5 = new WxMpTemplateData();
        data5.setName("nighttemp");
        data5.setValue(cast.getNighttemp() + "°C");
        dataList.add(data5);

        message.setData(dataList);

        mpService.getTemplateMsgService().sendTemplateMsg(message);
    }

}
