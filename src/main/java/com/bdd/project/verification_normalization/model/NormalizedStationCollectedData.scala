package com.bdd.project.verification_normalization.model
import java.sql.Timestamp

case class NormalizedStationCollectedData(stationId:Integer,datetime:Timestamp,
                                          channel:String, value:Double, status:Int, valid:Boolean) extends Serializable {


  @transient
  def isValid(validChannelsNames:Seq[String]): Boolean = {
    // TODO put the 2 in config file ?
    valid==true && status ==1 && validChannelsNames.contains(channel)
  }

}