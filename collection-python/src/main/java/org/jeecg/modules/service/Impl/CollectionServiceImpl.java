package org.jeecg.modules.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.constant.RedisKeyConstant;
import org.jeecg.modules.entity.CollectionLog;
import org.jeecg.modules.service.ICollectionLogService;
import org.jeecg.modules.service.ICollectionService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author 余吉钊
 * @date 2024/1/18 15:53
 */
@Service
@Slf4j
public class CollectionServiceImpl implements ICollectionService {


    private String pyFilePath =
            System.getProperty("user.dir") + "\\collection-python\\src\\main\\resources\\python\\server.py";

    private String pyPath = "E:\\Anaconda3\\python.exe";

    @Resource
    private ICollectionLogService logService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    @Async("PythonAsyncTask")
    public void pyCollectionBegin(String monitorUrl,String userConfigId, LoginUser user) {
        String redisKey = RedisKeyConstant.PY_COLLECTION_KEY+ userConfigId + user.getId();
        String cmdStr = pyPath + " " + pyFilePath + " " + monitorUrl + " " + user.getId() + " " + redisKey;
        log.info("启动路径,{}",cmdStr);
        try {
            Runtime.getRuntime().exec(cmdStr);
            //每个月月底自动关闭，需要重新开启
            redisTemplate.opsForValue().set(redisKey, 0);
            String log = "用户："+user.getId()+"开始摄像头搜集";
            logService.save(new CollectionLog(log,user.getAvatar()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutDownAsync(String userId,String userConfigId) {
        String redisKey = RedisKeyConstant.PY_COLLECTION_KEY+ userConfigId + userId;
        redisTemplate.opsForValue().set(redisKey, 1);
    }
}
