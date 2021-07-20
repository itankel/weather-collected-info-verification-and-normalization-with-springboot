package com.bdd.project.verification_normalization.services

import org.apache.spark.sql.{Dataset, Row}

trait WeatherInfoReader {
  def readEvents(): Dataset[Row]
}
