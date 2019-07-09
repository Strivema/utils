package com.ray.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Ray.Ma
 * @date 2019/7/9 11:13
 */
public class JsonUtils {

    public static String beanToJSON(Object o) {
        return o == null ? null : JSON.toJSONString(o);
    }

    public static <T> T jsonToBean(String json, Class<T> c) {
        return StringUtils.isBlank(json) ? null : JSON.toJavaObject(JSON.parseObject(json), c);
    }

    public static <T> T jsonArrayToBean(String json, Class<T> c) {
        return StringUtils.isBlank(json) ? null : JSON.toJavaObject(JSON.parseArray(json), c);
    }
}