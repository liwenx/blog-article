package com.yy.blog.article.commons.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author zhengjm5
 * @date 2018/1/22 21:08
 */
public class JsonUtil {

    private static final Logger LOG = Logger.getLogger(JsonUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtil() {
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        if(StringUtils.isBlank(jsonString)) {
            return null;
        } else {
            try {
                return OBJECT_MAPPER.readValue(jsonString, clazz);
            } catch (IOException var3) {
                LOG.error("parse json string error:" + jsonString, var3);
                return null;
            }
        }
    }

    public static <T> T fromJson(String jsonString, TypeReference<T> typeReference) {
        if(StringUtils.isBlank(jsonString)) {
            return null;
        } else {
            try {
                return OBJECT_MAPPER.readValue(jsonString, typeReference);
            } catch (IOException var3) {
                LOG.error("parse json string error:" + jsonString, var3);
                return null;
            }
        }
    }

    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (IOException var2) {
            LOG.warn("write to json string error:" + object, var2);
            return null;
        }
    }

    public static ObjectMapper getMapper() {
        return OBJECT_MAPPER;
    }

    static {
        OBJECT_MAPPER.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        OBJECT_MAPPER.getDeserializationConfig().set(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        OBJECT_MAPPER.getSerializationConfig().setDateFormat(df);
        OBJECT_MAPPER.getDeserializationConfig().setDateFormat(df);
    }
}
