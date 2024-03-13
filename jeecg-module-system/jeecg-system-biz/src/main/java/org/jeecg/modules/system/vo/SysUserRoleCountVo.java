package org.jeecg.modules.system.vo;

import lombok.Data;

/**
 * @Description:
 * @author: wangshuai
 * @date: 2023/11/01
 */
@Data
public class SysUserRoleCountVo {
    /**
     * 角色id
     */
    private String id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色下的用户数量
     */
    private Long count;
}
