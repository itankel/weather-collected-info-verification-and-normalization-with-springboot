package com.bdd.project.verification_normalization.model


case class StationCollectedData ( stationId:Integer, data : List[CollectedChannelsSample]) extends Serializable {
}
