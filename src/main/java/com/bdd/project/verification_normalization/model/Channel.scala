package com.bdd.project.verification_normalization.model


case class Channel  (id :Int, name:String ,alias:String,value:Double,status:Int,valid:Boolean,
                     description:String ) extends Serializable {
}