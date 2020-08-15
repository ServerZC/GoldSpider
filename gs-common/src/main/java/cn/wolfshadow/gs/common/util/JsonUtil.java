package cn.wolfshadow.gs.common.util;


import com.alibaba.fastjson.JSON;

/**
 * JSON相关工具类
 */
public class JsonUtil {


    public static Object json2Object(String json, Class<?> clzz){
        return JSON.parseObject(json,clzz);
    }
}
