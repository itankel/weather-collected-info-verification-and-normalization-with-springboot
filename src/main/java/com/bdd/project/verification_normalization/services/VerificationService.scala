package com.bdd.project.verification_normalization.services

import com.bdd.project.verification_normalization.model.NormalizedStationCollectedData
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.springframework.stereotype.Component


@Component
class VerificationService(sparkSession: SparkSession) extends VerificationServiceApi {
  private val COL_STATUS="status"
  private val COL_VALID="valid"

  def verifyData(datasetToValidate:Dataset[Row],stationsValidChannels:Seq[String]) :Dataset[Row] ={
    import sparkSession.implicits._

    val normalizedStationCollectedDS = datasetToValidate.as[NormalizedStationCollectedData]
      .filter(_.isValid(stationsValidChannels))
      .drop(COL_VALID)
      .drop(COL_STATUS)

    normalizedStationCollectedDS
  }

}
