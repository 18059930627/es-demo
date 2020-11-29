package com.qf.elasticsearch.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;

/**
 * @Author chenzhongjun
 * Json工具类
 * @Date 2020/11/28
 */
public class JSONUtils {

    private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class.getName());

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * object 2 json
     * @param object
     * @return
     */
    public static String objectToJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("objectToJson fail. ex:{}", e);
        }
        return null;
    }

    /**
     * json 2 object
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T JsonToObject(String json, Class<T> clazz){
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error("JsonToObject fail. ex:{}", e);
        }
        return null;
    }


    static class Student {
        private String name;

        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public Student(){

        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static void main(String[] args){
        Student student = new Student("11",22); 
       // System.out.println(JSONUtils.objectToJson(student));

        Student student1 = JSONUtils.JsonToObject(JSONUtils.objectToJson(student), Student.class);
        System.out.println(student1);
    }
}
