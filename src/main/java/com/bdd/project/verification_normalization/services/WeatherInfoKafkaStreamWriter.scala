package com.bdd.project.verification_normalization.services

import com.bdd.project.verification_normalization.configuration.ApiConfiguration
import com.bdd.project.verification_normalization.model.KafkaParameters
import org.apache.spark.sql.streaming.{OutputMode, StreamingQuery}
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.springframework.stereotype.Component

@Component
class WeatherInfoKafkaStreamWriter(sparkSession: SparkSession, apiConfiguration: ApiConfiguration) extends WeatherInfoWriter {
  private val WEATHER_INFO_APPROVED_TOPIC = "weather_info_verified_data" // this is not configurable from my point of view
  private val NUM_ROWS = "numRows"

  import sparkSession.implicits._

  def writeData(datasetToWrite: Dataset[Row]): StreamingQuery = {

    val streamingQuery =
      datasetToWrite.toJSON
      .writeStream
      .format(KafkaParameters.KAFKA_FORMAT)
      .option(KafkaParameters.KAFKA_BOOTSTRAP_SERVER, apiConfiguration.getBootstrapServer())
      .option(KafkaParameters.CHECKPOINT, apiConfiguration.getKafkaCheckpointPath())
      .option(KafkaParameters.TOPIC, WEATHER_INFO_APPROVED_TOPIC)
      .outputMode(OutputMode.Append())
      .option(NUM_ROWS, 3)
      .start()

    streamingQuery

  }
}
