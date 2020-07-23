package com.kkasztel.sensorstats.service

import com.kkasztel.sensorstats.model.{HumidityRecord, HumidityValue}

object HumidityRecordParser {

  val delimiter = ','

  def parse(input: String): Option[HumidityRecord] =
    Option(input)
      .map(_.split(delimiter))
      .filter(_.length == 2)
      .map(a => HumidityRecord(a(0), HumidityValue(a(1))))
}
