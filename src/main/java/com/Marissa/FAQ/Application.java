package com.Marissa.FAQ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@ComponentScan(basePackages={"com.Marissa"})
public class Application  {
    //  文件上传配置
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
        factory.setMaxFileSize("500MB"); //KB,MB
        // 设置总上传数据总大小
        factory.setMaxRequestSize("600MB");
        return factory.createMultipartConfig();
    }
   /* @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // 1、需要先定义一个converter 转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 2、添加fastJson 的配置信息，比如：是否要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        // 3、在convert 中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        // 4、将convert 添加到converters当中
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }*/
   /* @Bean
    public ObjectMapper ObjectMapper(){
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper;
    }*/

   /* //将json字符串转换成对象
    Map map = objectMapper.readValue(jsonString, Map.class);
    //转换对象类型
    SomethingPOJO pojo = objectMapper.convertValue(map, SomethingPOJO.class);
    //将对象转换成json字符串
    Sting string = objectMapper.writeValueAsString(pojo);
    //将json字符串转换成List
    JavaType javaType = mapper.getTypeFactory()
            .constructParametricType(List.class, Person.class);
    List<Person> jsonToPersonList = objectMapper.readValue(arrayToJson, mapType);*/

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}