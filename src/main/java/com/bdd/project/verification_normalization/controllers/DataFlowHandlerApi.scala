package com.bdd.project.verification_normalization.controllers

import org.apache.spark.sql.streaming.StreamingQuery

trait DataFlowHandlerApi {
  def handleFlow() : StreamingQuery
}
