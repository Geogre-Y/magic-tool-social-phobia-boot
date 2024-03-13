package org.jeecg.modules.service;

import org.jeecg.common.system.vo.LoginUser;

/**
 * @author 余吉钊
 * @date 2024/1/18 15:53
 */
public interface ICollectionService {


    void pyCollectionBegin(String monitorUrl, String userConfigId, LoginUser user);

    void shutDownAsync(String userId,String userConfigId);
}
