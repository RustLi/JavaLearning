package org.lwl.test.trans;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public final class JacksonUtil {
    private static final ObjectMapper MAPPER;

    private JacksonUtil() {}
    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String obj2Str(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (IOException e) {
            log.error("obj2Str error", e);
            return null;
        }
    }

    public static void writeObj(OutputStream out, Object value) throws IOException {
        MAPPER.writeValue(out, value);
    }

    public static <T> T str2Obj(String s, Class<T> valueType) throws IOException {
        JavaType javaType = getJavaType(valueType, null);
        return MAPPER.readValue(s, javaType);
    }

    @SuppressWarnings("deprecation")
	protected static JavaType getJavaType(Type type, Class<?> contextClass) {
        return (contextClass != null) ? MAPPER.getTypeFactory().constructType(type, contextClass) : MAPPER
                .constructType(type);
    }

    public static final <T> T str2Obj(String s, TypeReference<T> valueType) throws IOException {
        return MAPPER.readValue(s, valueType);
    }

    public static final <T> List<T> str2List(String s, Class<T> valueType) throws IOException {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, valueType);
        return MAPPER.readValue(s, javaType);
    }

    public static final <T> T readObj(InputStream in, Class<T> valueType) throws IOException {
        return MAPPER.readValue(in, valueType);
    }

    @SuppressWarnings("unchecked")
    public static final <T> T readObj(InputStream in, JavaType valueType) throws IOException {
        return (T) MAPPER.readValue(in, valueType);
    }

    /**
     * 对象转map
     *
     * @param object 对象
     * @return 转换后的map
     */
    public static Map<String, Object> toMap(Object object) {
        try {
            return MAPPER.readValue(obj2Str(object), new TypeReference<HashMap>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json反序列化为map
     *
     * @param jsonString json字符串
     * @return 反序列化后的map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String jsonString) {
        try {
            return str2Obj(jsonString, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}