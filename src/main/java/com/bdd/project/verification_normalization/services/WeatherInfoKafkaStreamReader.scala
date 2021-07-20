package com.bdd.project.verification_normalization.services

import com.bdd.project.verification_normalization.configuration.ApiConfiguration
import com.bdd.project.verification_normalization.model.KafkaParameters
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.springframework.stereotype.Component

@Component
class WeatherInfoKafkaStreamReader(sparkSession: SparkSession
                                   , apiConfiguration: ApiConfiguration) extends WeatherInfoReader {

  private val WEATHER_INFO_ROW_TOPIC = "weather_info_latest_raw_data" // this is not configurable from my point of view

  def readEvents(): Dataset[Row] = {
    var inputDF = sparkSession
      .readStream
      .format(KafkaParameters.KAFKA_FORMAT)
      .option(KafkaParameters.KAFKA_BOOTSTRAP_SERVER, apiConfiguration.getBootstrapServer())
      .option(KafkaParameters.SUBSCRIBE, WEATHER_INFO_ROW_TOPIC)
      .load
      .select(col(KafkaParameters.VALUE_COL).cast(StringType).alias(KafkaParameters.VALUE_COL))

    inputDF.printSchema();
    inputDF

  }

}
