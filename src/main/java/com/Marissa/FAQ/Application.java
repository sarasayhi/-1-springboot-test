package com.Marissa.FAQ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@ComponentScan(basePackages={"com.Marissa"})
public class Application  {
    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
        factory.setMaxFileSize("500MB"); //KB,MB
        // 设置总上传数据总大小
        factory.setMaxRequestSize("600MB");
        //Sets the directory location wherefiles will be stored.
//        factory.setLocation("D:\\test");
        return factory.createMultipartConfig();
    }


    //        extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
//    @Configuration
//    public class CommonConfig {

//        @Bean
//        public MultipartConfigElement multipartConfigElement() {
//            MultipartConfigFactory factory = new MultipartConfigFactory();
//            factory.setMaxFileSize(1024L * 1024L);
//            return factory.createMultipartConfig();
//        }
//    }
   /* implements EmbeddedServletContainerCustomizer
   @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
            container.setPort(7777);
    }*/
/*    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        //自定义应用程序或调用application.sources（...）添加源
        //因为我们的例子本身是一个@Configuration类（通过@SpringBootApplication）
        //我们实际上不需要重写这个方法。
        return application.sources(Application.class);
    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }*/

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return configureApplication(builder);
    }

    public static void main(String[] args) {
        configureApplication(new SpringApplicationBuilder()).run(args);
    }

    private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
//                .bannerMode(Banner.Mode.OFF);
    }*/

}