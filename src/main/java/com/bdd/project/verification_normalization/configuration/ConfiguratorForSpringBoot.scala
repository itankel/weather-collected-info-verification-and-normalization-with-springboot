package com.bdd.project.verification_normalization.configuration

import org.apache.spark.sql.SparkSession
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class ConfiguratorForSpringBoot {

  @Bean
  def sparkSessionDev: SparkSession = SparkSession.builder.master("local[*]").appName("Weather-agro-Project")
    .getOrCreate


}
