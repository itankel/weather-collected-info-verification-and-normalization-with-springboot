package com.bdd.project.verification_normalization.controllers

import com.bdd.project.verification_normalization.configuration.ApiConfiguration
import com.bdd.project.verification_normalization.services.{NormalizationService, VerificationService, WeatherInfoKafkaStreamReader, WeatherInfoKafkaStreamWriter}
import org.apache.spark.sql.streaming.{StreamingQuery, StreamingQueryException}
import org.springframework.stereotype.Component

import scala.collection.JavaConverters.asScalaBufferConverter


@Component
class DataFlowHandler(weatherInfoKafkaStreamReader: WeatherInfoKafkaStreamReader,
                      normalizationService: NormalizationService, validationService: VerificationService,
                      kafkaWriterService: WeatherInfoKafkaStreamWriter,
                      apiConfiguration: ApiConfiguration) extends DataFlowHandlerApi {

  private val validChannelsNames: Seq[String] = apiConfiguration.getValidWeatherParams().asScala

  def handleFlow(): StreamingQuery = {
    var eventsData = weatherInfoKafkaStreamReader.readEvents()
    var normalizedDataset = normalizationService.normalizeData(eventsData)
    var validatedDataset = validationService.verifyData(normalizedDataset, validChannelsNames)
    var streamQuery = kafkaWriterService.writeData(validatedDataset)

    try streamQuery.awaitTermination()
    catch {
      case e: StreamingQueryException =>
        println("Exception while waiting for query to end {}." + e.getMessage + " - " + e.toString())
    }
    streamQuery
  }


}
