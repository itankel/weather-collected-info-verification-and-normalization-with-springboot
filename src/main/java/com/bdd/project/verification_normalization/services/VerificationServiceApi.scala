package com.bdd.project.verification_normalization.services

import org.apache.spark.sql.{Dataset, Row}

trait VerificationServiceApi {
  def verifyData(datasetToValidate:Dataset[Row],stationsValidChannels:Seq[String]) :Dataset[Row]
}
