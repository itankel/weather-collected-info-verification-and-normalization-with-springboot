package com.bdd.project.verification_normalization.controllers;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainFlowManager {
    @Autowired
    private DataFlowHandler dataFlowHandler;
    @Autowired
    private SparkSession sparkSession;


    public void startFlow() {
        StreamingQuery streamingQuery = dataFlowHandler.handleFlow();
        // define the gracefully shutdown
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                streamingQuery.stop();
                while (streamingQuery.isActive()) {
                }
                sparkSession.close();
            }
        });

        try {
            streamingQuery.awaitTermination();
        } catch (StreamingQueryException e) {
            e.printStackTrace();
        } finally {
            sparkSession.close();
        }


    }


}
