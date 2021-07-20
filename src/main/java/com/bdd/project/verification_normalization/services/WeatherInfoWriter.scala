package com.bdd.project.verification_normalization.services

import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.{Dataset, Row}

trait WeatherInfoWriter {

  def writeData(datasetToWrite: Dataset[Row]) :StreamingQuery
}
