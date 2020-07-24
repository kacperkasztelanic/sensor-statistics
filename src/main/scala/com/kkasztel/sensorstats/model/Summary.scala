package com.kkasztel.sensorstats.model

case class Summary(sensorResults: Seq[SensorResult], fileCount: Int) {

  override def toString: String = {
    s"""Num of processed files: $fileCount
       |Num of processed measurements: ${sensorResults.map(_.count).sum}
       |Num of failed measurements: ${sensorResults.map(_.errors).sum}

       |Sensors with highest avg humidity:

       |sensor-id,min,avg,max
       |${sensorResults.sorted(Ordering[SensorResult].reverse).mkString("\n")}""".stripMargin
  }
}
