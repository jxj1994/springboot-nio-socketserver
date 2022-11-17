package com.jiang.springbootniosocketserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class JsonUtils {
    private static ObjectMapper OBJECT_MAPPER = new
            ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    static {
        // 忽略json字符串中不识别的属性
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略无法转换的对象
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // PrettyPrinter 格式化输出
        OBJECT_MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
        // NULL不参与序列化
        //OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 指定时区
        OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        // 日期类型字符串处理
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT));

        // java8 LocalDateTime日期处理
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

        OBJECT_MAPPER.registerModule(javaTimeModule);
    }

    public JsonUtils() {
        // 忽略json字符串中不识别的属性
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略无法转换的对象
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // PrettyPrinter 格式化输出
        OBJECT_MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
        // NULL不参与序列化
        //OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 指定时区
        OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        // 日期类型字符串处理
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT));

        // java8 LocalDateTime日期处理
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

        OBJECT_MAPPER.registerModule(javaTimeModule);
    }

    /**
     * json转化成单一对象
     *
     * @param jsonString
     * @param object
     * @return
     */
    public static <T> T jsonToObject(String jsonString, Class<T> object) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, object);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json转化成list对象
     *
     * @param jsonString
     * @param clazz
     * @return
     */
    //public <T> T get(String key,Class<T> clazz){
    public static <T> List<T> jsonToListObject(String jsonString, Class<T> clazz) {
        try {
            //传入对象泛型
            JavaType listType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz);

            return OBJECT_MAPPER.readValue(jsonString, listType);


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 单一对象转化成json
     *
     * @param object
     * @return
     */
    public static String ObjectToStr(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {

            e.printStackTrace();
            return null;
        }
    }

    /**
     * list转json
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> String listToStr(List<T> list) {
        try {
            return OBJECT_MAPPER.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JsonNode strToJsonNode(String str) {
        JsonNode node = null;
        try {
            node = OBJECT_MAPPER.readTree(str);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return node;
    }

    public static JsonNode mapToJsonNode(Map<String, Object> map) {
        JsonNode jsonNode = OBJECT_MAPPER.valueToTree(map);
        return jsonNode;
    }


    public static <T> T parseObject(String str, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(str, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static <T> String toJSONString(T clazz) {
        try {
            return OBJECT_MAPPER.writeValueAsString(clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

