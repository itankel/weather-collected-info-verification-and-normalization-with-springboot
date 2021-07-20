package com.bdd.project.verification_normalization.services

import org.apache.spark.sql.{Dataset, Row}

trait NormalizationServiceApi {
  def normalizeData(datasetToNormalize: Dataset[Row]): Dataset[Row]
}
