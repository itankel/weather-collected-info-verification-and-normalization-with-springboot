package com.bdd.project.verification_normalization.model

import java.sql.Timestamp


case class CollectedChannelsSample (datetime:Timestamp, channels : List[Channel])extends Serializable {
}
