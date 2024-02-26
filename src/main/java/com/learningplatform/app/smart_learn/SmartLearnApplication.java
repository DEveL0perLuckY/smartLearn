package com.learningplatform.app.smart_learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SmartLearnApplication {

    public static void main(final String[] args) {
        ConfigurableApplicationContext c = SpringApplication.run(SmartLearnApplication.class, args);
        System.out.println("SmartLearn application " + c.getEnvironment().getActiveProfiles()[0].toString());
    }

}
