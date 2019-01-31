package com.modules.demo2;

import com.modules.demo2.bean.Dog;
import com.modules.demo2.bean.UserBean1;
import com.modules.demo2.bean.UserBean2;
import com.modules.demo2.bean.UserBean2NoInIOC;
import com.modules.demo2.config.MyConfig;
import com.modules.demo2.mapper.TestMapper;
import com.modules.demo2.mapper.domain.Test;
import com.modules.demo2.util.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import java.util.Arrays;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.modules.demo2.filter")
@Import({UserBean2NoInIOC.class, MyConfig.class})
public class Demo2Application {

    private static final Logger log = LoggerFactory.getLogger(Demo2Application.class);

    @Autowired
    private ApplicationContext appCtx2;

    @Autowired
    private UserBean1 userBean1;

    @Autowired
    private UserBean2 userBean2;

    @Autowired
    private ApplicationContextProvider applicationContextProvider;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Demo2Application.class, args);
        UserBean2 userBean2 = context.getBean(UserBean2.class);
        userBean2.setAge(11);
        log.info("userBean2 age in main {}", userBean2.getAge());
        UserBean2NoInIOC userBean2NoInIOC = context.getBean(UserBean2NoInIOC.class);
        userBean2NoInIOC.setName("userBean2NoInIOC~name");
        log.info("userBean2NoInIOC name in main {}", userBean2NoInIOC.getName());
        Dog dog = context.getBean(Dog.class);
        dog.setName("my dog");
        log.info("{}", dog.getName());
        DefaultServletHttpRequestHandler defaultServletHttpRequestHandler = context.getBean("defaultServlet", DefaultServletHttpRequestHandler.class);


        log.info("app started");
    }

    @Bean
    public CommandLineRunner runner(ApplicationContext appCtx){
        return args -> {

            log.info("inspect beans provided by spring boot:");
            String[] beanNames = appCtx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for(String oneBeanName:beanNames){
                log.info("one bean --> {}", oneBeanName);
            }
            mainRun();
        };
    }

    private void mainRun(){
        userBean1.setName("AAAAAAA");
        log.info("bean userBean1 getName {}", userBean1.getName());

        userBean2.setAge(1);
        log.info("bean userBean2 getAge {}", userBean2.getAge());

        UserBean1 userBean1_1 = appCtx2.getBean("userBean1", UserBean1.class);
        userBean1_1.setName("userBean1_1_name");
        log.info("appCtx getBean name is {}", userBean1_1.getName());

        UserBean1 userBean1_1_2 = appCtx2.getBean(UserBean1.class);
        userBean1_1_2.setName("userBean1_1_name_2");
        log.info("appCtx getBean name is {}", userBean1_1_2.getName());

        UserBean2 userBean2_2 = applicationContextProvider.getApplicationContext().getBean(UserBean2.class);
        userBean2_2.setAge(22);
        log.info("userBean2_2 age is {}", userBean2_2.getAge());

    }
}
