package com.bdd.project.verification_normalization;

import com.bdd.project.verification_normalization.controllers.MainFlowManager;
import org.apache.spark.sql.SparkSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class MainApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);

        MainFlowManager mf = context.getBean(MainFlowManager.class);
        mf.startFlow();
        context.getBean(SparkSession.class).close();


    }

}
