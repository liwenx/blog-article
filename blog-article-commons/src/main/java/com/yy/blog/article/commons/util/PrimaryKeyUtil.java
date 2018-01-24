package com.yy.blog.article.commons.util;

/**
 * 获取主键方法`
 * @author zhengjm5
 * @date 2018/1/24 16:14
 */
public class PrimaryKeyUtil {

    private static SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1, 0);

    private PrimaryKeyUtil(){

    }

    public static String getPrimaryKey(){
        return String.valueOf(snowflakeIdWorker.nextId());
    }
}
