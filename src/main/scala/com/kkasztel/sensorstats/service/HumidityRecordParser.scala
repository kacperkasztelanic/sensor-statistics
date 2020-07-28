package com.kkasztel.sensorstats.service

import com.kkasztel.sensorstats.model.{HumidityRecord, HumidityValue}

object HumidityRecordParser {

  val delimiter = ','

  def parse(input: String): Option[HumidityRecord] =
    Option(input)
      .map(_.split(delimiter))
      .map(_.map(_.trim).filterNot(_.isEmpty))
      .filter(_.length == 2)
      .map(a => HumidityRecord(a(0), HumidityValue(a(1))))
}
