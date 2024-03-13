package org.jeecg.common.aspect.annotation;

import java.lang.annotation.*;

/**
 * 动态table切换
 *
 * @Author 余维华
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicTable {
    /**
     * 需要动态解析的表名
     * @return
     */
    String value();
}
