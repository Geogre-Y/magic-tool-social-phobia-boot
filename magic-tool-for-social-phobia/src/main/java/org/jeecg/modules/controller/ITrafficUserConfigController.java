package org.jeecg.modules.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkcoding.http.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.entity.ITrafficUserConfig;
import org.jeecg.modules.enums.TrafficStateEnum;
import org.jeecg.modules.service.ITrafficUserConfigService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 余吉钊
 * @date 2024/2/19 17:12
 */
@RestController
@RequestMapping("/userConfig")
@Api(tags = "设置人流量监控")
public class ITrafficUserConfigController {

    @Autowired
    private ITrafficUserConfigService service;

    @Autowired
    private ISysUserService sysUserService;

    @PostMapping("/set")
    @ApiOperation("新增监控配置")
    Result<String> setConfig(@RequestBody ITrafficUserConfig req) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String userid = sysUser.getId();
        req.setUserId(userid);
        if (StringUtil.isEmpty(sysUser.getWxOpenId())&& req.getState().equals(1)) {
            return Result.error("新增配置失败，用户未关注公众号！");
        }
        req.setWxOpenId(sysUser.getWxOpenId());

        service.save(req);
        return Result.ok("新增成功");
    }

    @PostMapping("/update")
    @ApiOperation("修改监控配置")
    Result<String> updateConfig(@RequestBody ITrafficUserConfig userConfig) {
        if (userConfig.getId() == null) {
            return Result.error("id是必填项");
        }
        ITrafficUserConfig select = service.getById(userConfig.getId());
        if (Objects.equals(select.getState(), TrafficStateEnum.All.getValue())){
            return Result.error("正在监控中的配置不可修改");
        }
        service.updateById(userConfig);
        return Result.OK("修改成功");
    }

    @PostMapping("/del")
    @ApiOperation("删除监控配置")
    Result<String> delConfig(@RequestBody ITrafficUserConfig userConfig) {
        if (userConfig.getId() == null) {
            return Result.error("id是必填项");
        }
        ITrafficUserConfig select = service.getById(userConfig.getId());
        if (Objects.equals(select.getState(), TrafficStateEnum.All.getValue())){
            return Result.error("正在监控中的配置不可删除");
        }
        service.removeById(userConfig);
        return Result.OK("删除成功");
    }



    @GetMapping("/get")
    @ApiOperation("获取所有的监控配置")
    Result<IPage<ITrafficUserConfig>> getUserConfigs(@RequestParam(name = "pageNo",
                                                                   defaultValue = "1") Integer pageNo, @RequestParam(
            name = "pageSize", defaultValue = "10") Integer pageSize) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String userid = sysUser.getId();
        QueryWrapper<ITrafficUserConfig> queryWrapper = new QueryWrapper<>();
        List<SysUser> userList = null;
        if (sysUser.getUserIdentity() !=null && sysUser.getUserIdentity() == 2) {
            userList = sysUserService.queryByDepIds(Collections.singletonList(sysUser.getDepartIds()),
                    sysUser.getUsername());
            SysUser loginSysUser = new SysUser();
            BeanUtil.copyProperties(sysUser, loginSysUser);
            userList.add(loginSysUser);
            queryWrapper.in("user_id", userList.stream().map(SysUser::getId).collect(Collectors.toList()));
        } else {
            queryWrapper.eq("user_id", userid);
        }
        Page<ITrafficUserConfig> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNo);
        IPage<ITrafficUserConfig> list = service.page(page, queryWrapper);
        if (CollectionUtil.isEmpty(list.getRecords())) {
            return Result.ok(list);
        } else {
            Map<String, SysUser> sysUserMap;
            if (CollectionUtil.isEmpty(userList)) {
                List<String> userIds =
                        list.getRecords().stream().map(ITrafficUserConfig::getUserId).collect(Collectors.toList());
                QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.in("id", userIds);
                List<SysUser> sysUsers = sysUserService.list(userQueryWrapper);
                sysUserMap = sysUsers.stream().collect(Collectors.toMap(SysUser::getId, v -> v));
            } else {
                sysUserMap = userList.stream().collect(Collectors.toMap(SysUser::getId, v -> v));
            }
            list.getRecords().forEach(v -> v.setUserName(sysUserMap.get(v.getUserId()).getRealname()));
            return Result.ok(list);
        }
    }
}
