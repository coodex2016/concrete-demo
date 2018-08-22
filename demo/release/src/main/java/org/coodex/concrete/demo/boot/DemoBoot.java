package org.coodex.concrete.demo.boot;

import org.coodex.concrete.demo.api.DemoService;
import org.coodex.concrete.spring.ConcreteSpringConfiguration;
import org.coodex.concrete.support.jsr339.ConcreteJSR339Application;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "org.coodex.concrete.demo.impl")
@Configuration
@Import(ConcreteSpringConfiguration.class)
public class DemoBoot extends SpringBootServletInitializer {

    /**
     * 运行 spring boot
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoBoot.class, args);
    }

    /**
     * 定义一个jaxrsServlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean jaxrsServlet() {
        ServletContainer container = new ServletContainer();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(
                container, "/jaxrs/*");

        // 按照jaxrs规范，指定Application的className
        registrationBean.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS,
                JaxRSApplication.class.getName());

        registrationBean.setName("demo");

        // 异步支持（重要：servlet 3.0/jaxrs 2.0 都支持异步，性能和可管理性都有大幅提升）
        registrationBean.setAsyncSupported(true);
        return registrationBean;
    }

    /**
     * jsr339(jaxrs 2.0)规范的Application
     */
    public static class JaxRSApplication extends ConcreteJSR339Application {
        public JaxRSApplication() {
            register(
                    // 使用 jackson 作为 jaxrs的序列化和反序列化实现
                    JacksonFeature.class,
                    // 注册 我们写的api
                    DemoService.class);
        }
    }
}
