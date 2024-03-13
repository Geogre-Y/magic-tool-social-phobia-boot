package org.jeecg.modules.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.entity.CollectionLog;
import org.jeecg.modules.entity.ITrafficUserConfig;
import org.jeecg.modules.enums.TrafficStateEnum;
import org.jeecg.modules.service.ICollectionLogService;
import org.jeecg.modules.service.ICollectionService;
import org.jeecg.modules.service.ITrafficUserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 余吉钊
 * @date 2024/1/18 16:26
 */
@RestController
@RequestMapping("/collection")
@Api(tags = "py信息收集")
public class CollectionController {

    @Autowired
    private ICollectionService collectionService;

    @Autowired
    private ITrafficUserConfigService userConfigService;

    @Autowired
    private ICollectionLogService logService;

    @GetMapping("/monitor/start")
    @ApiOperation("开始收集")
    public Result<String> start(@RequestParam("userConfigId")String userConfigId) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String userid = sysUser.getId();
        if (StringUtils.isEmpty(userid)) {
            return Result.error("未登录");
        }
        QueryWrapper<ITrafficUserConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userConfigId);
        queryWrapper.eq("user_id",userid);
        ITrafficUserConfig userConfig = userConfigService.getOne(queryWrapper);
        if (ObjectUtil.isNull(userConfig)){
            return Result.error("配置不存在或配置不属于本人");
        }
        if (StringUtils.isEmpty(userConfig.getMonitorId())) {
            return Result.error("推流地址不正确");
        }
        collectionService.pyCollectionBegin(userConfig.getMonitorId(), userConfig.getId(),sysUser);
        userConfig.setState(TrafficStateEnum.All.getValue());
        userConfigService.updateById(userConfig);
        return Result.ok("python项目启动成功!");
    }

    @GetMapping("/monitor/shutdown")
    @ApiOperation("终止收集")
    public Result<String> shutdown(@RequestParam("userConfigId")String userConfigId) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        QueryWrapper<ITrafficUserConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userConfigId);
        if (!sysUser.getUserIdentity().equals(2)){
            queryWrapper.eq("user_id",sysUser.getId());
        }
        ITrafficUserConfig userConfig = userConfigService.getOne(queryWrapper);
        if (ObjectUtil.isNull(userConfig)){
            return Result.error("配置不存在或配置不属于您");
        }
        collectionService.shutDownAsync(sysUser.getId(),userConfig.getId());
        userConfig.setState(TrafficStateEnum.BASE.getValue());
        userConfigService.updateById(userConfig);
        logService.save(new CollectionLog("用户:"+sysUser.getId()+"停止收集",sysUser.getAvatar()));
        return Result.ok("关闭成功!");
    }

    @GetMapping("/monitor/log")
    @ApiOperation("获取收集日志")
    public Result<List<CollectionLog>> getLog() {
        QueryWrapper<CollectionLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return Result.ok(logService.list(queryWrapper));
    }
}
