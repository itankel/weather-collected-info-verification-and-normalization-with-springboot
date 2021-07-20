package com.bdd.project.verification_normalization.services


import com.bdd.project.verification_normalization.model.StationCollectedData
import org.apache.spark.sql.functions.{col, element_at, explode, from_json}
import org.apache.spark.sql.{Dataset, Encoders, Row, SparkSession}
import org.springframework.stereotype.Component


@Component
class NormalizationService(sparkSession: SparkSession) extends NormalizationServiceApi {
  private val ALL_COL_PARSED_JSON = "parsed_json.*"
  private val COL_PARSED_JSON = "parsed_json"
  private val COL_VALUE = "value"
  private val COL_STATION_DATA="station_data"
  private val COL_DATA="data"
  private val COL_STATION_ID ="stationId"
  private val COL_DATETIME="datetime"
  private val COL_CHANNELS="channels"
  private val COL_CHANNEL_I="channeli"
  private val COL_CHANNEL="channel"
  private val COL_STATUS="status"
  private val COL_VALID="valid"
  private val COL_NAME="name"
  private val FIRST_POS = 1


  def normalizeData(datasetToNormalize: Dataset[Row]): Dataset[Row] = {
    val stationCollectedDataEncoder = Encoders.product[StationCollectedData]
    val inSchema = stationCollectedDataEncoder.schema

    val inputDF = datasetToNormalize
      .withColumn(COL_PARSED_JSON, from_json(col(COL_VALUE), inSchema))
      .select(ALL_COL_PARSED_JSON)


    var flattenedDF = inputDF
      .select(col(COL_STATION_ID),col(COL_DATA))
      .withColumn(COL_STATION_DATA, element_at(col(COL_DATA), FIRST_POS))
      .drop(COL_DATA)

    flattenedDF = flattenedDF.select(col(COL_STATION_ID), col(COL_STATION_DATA)(COL_DATETIME).as(COL_DATETIME),
      col(COL_STATION_DATA)(COL_CHANNELS).as(COL_CHANNELS))

    flattenedDF.printSchema()//for debug

    flattenedDF = flattenedDF.withColumn(COL_CHANNEL_I, explode(flattenedDF.col(COL_CHANNELS)))

    flattenedDF.printSchema()//for debug

    flattenedDF = flattenedDF
      .withColumn(COL_CHANNEL, flattenedDF.col(COL_CHANNEL_I)(COL_NAME))
      .withColumn(COL_VALUE, flattenedDF.col(COL_CHANNEL_I)(COL_VALUE))
      .withColumn(COL_STATUS, flattenedDF.col(COL_CHANNEL_I)(COL_STATUS))
      .withColumn(COL_VALID, flattenedDF.col(COL_CHANNEL_I)(COL_VALID))
      .drop(COL_CHANNELS)
      .drop(COL_CHANNEL_I)


    flattenedDF.printSchema()//for debug
    flattenedDF
  }
}
