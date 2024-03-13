package org.jeecg.common.aspect.annotation;

import java.lang.annotation.*;

/**
 * 通过此注解声明的接口，自动实现字典翻译
 * 
 * @Author 余维华
 * @email cick-y@foxmail.com
 * @Date 2022年01月05日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoDict {

	/**
	 * 暂时无用
	 * @return
	 */
	String value() default "";

}
