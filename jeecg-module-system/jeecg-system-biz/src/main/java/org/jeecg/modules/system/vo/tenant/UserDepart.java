package org.jeecg.modules.system.vo.tenant;

import lombok.Data;

/**
 * 用户与部门信息
 * @Author 余维华
 * @Date 2023/2/17 10:10
 **/
@Data
public class UserDepart {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 部门名称
     */
    private String departName;
}
