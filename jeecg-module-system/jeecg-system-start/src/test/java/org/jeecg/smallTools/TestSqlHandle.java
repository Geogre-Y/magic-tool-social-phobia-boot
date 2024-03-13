package org.jeecg.smallTools;

import org.junit.Test;

/**
 * 测试sql分割、替换等操作
 * 
 * @Author 余维华
 * @date: 2023/11/01
 */
public class TestSqlHandle {

    /**
     * Where 分割测试
     */
    @Test
    public void testSqlSplitWhere() {
        String tableFilterSql = " select * from data.sys_user Where name='12312' and age>100";
        String[] arr = tableFilterSql.split(" (?i)where ");
        for (String sql : arr) {
            System.out.println("sql片段：" + sql);
        }
    }


    /**
     * Where 替换
     */
    @Test
    public void testSqlWhereReplace() {
        String input = " Where name='12312' and age>100";
        String pattern = "(?i)where "; // (?i) 表示不区分大小写

        String replacedString = input.replaceAll(pattern, "");

        System.out.println("替换前的字符串：" + input);
        System.out.println("替换后的字符串：" + replacedString);
    }
}
