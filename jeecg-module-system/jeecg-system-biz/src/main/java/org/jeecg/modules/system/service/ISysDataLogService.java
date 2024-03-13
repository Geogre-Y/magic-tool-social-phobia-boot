package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.entity.SysDataLog;

/**
 * @Description: 数据日志service接口
 * @Author 余维华
 */
public interface ISysDataLogService extends IService<SysDataLog> {
	
	/**
	 * 添加数据日志
	 * @param tableName
	 * @param dataId
	 * @param dataContent
	 */
	public void addDataLog(String tableName, String dataId, String dataContent);

}
