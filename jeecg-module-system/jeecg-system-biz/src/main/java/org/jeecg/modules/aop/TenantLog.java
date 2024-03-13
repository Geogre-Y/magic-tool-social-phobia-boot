package org.jeecg.modules.aop;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * 
 * @Author 余维华
 * @email cick-y@foxmail.com
 * @Date 2019年1月14日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TenantLog {

	/**
	 * 操作日志类型（1查询，2添加，3修改，4删除）
	 * 
	 * @return
	 */
	int value() default 0;

}
