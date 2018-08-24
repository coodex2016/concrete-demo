package org.coodex.concrete.demo.boot;

import org.coodex.concrete.accounts.simple.api.Login;
import org.coodex.concrete.accounts.simple.impl.SimpleAccountFactory;
import org.coodex.concrete.accounts.simple.impl.SimpleAccountLoginImpl;
import org.coodex.concrete.common.AccountFactory;
import org.coodex.concrete.core.intercept.RBACInterceptor;
import org.coodex.concrete.core.intercept.SignatureInterceptor;
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

    @Bean
    public SignatureInterceptor signatureInterceptor(){
        return new SignatureInterceptor();
    }

    /**
     * 注册RBAC的拦截器
     *
     * @return
     */
    @Bean
    public RBACInterceptor rbacInterceptor() {
        return new RBACInterceptor();
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
     * step 3.1 注册简单账户工厂
     *
     * @return
     */
    @Bean
    public AccountFactory accountFactory() {
        return new SimpleAccountFactory();
    }

    /**
     * step 3.1 注册简单账户的登录服务实现
     *
     * @return
     */
    @Bean
    public Login login() {
        return new SimpleAccountLoginImpl();
    }

    /**
     * jsr339(jaxrs 2.0)规范的Application
     */
    public static class JaxRSApplication extends ConcreteJSR339Application {
        public JaxRSApplication() {
            register(
                    // 使用 jackson 作为 jaxrs的序列化和反序列化实现
                    JacksonFeature.class,
                    // step 3.1 简单账户登录服务
                    Login.class);

            // 按照包名约定注册服务
            registerPackage(DemoService.class.getPackage().getName());
        }
    }
}
