package com.kkasztel.sensorstats.service

import com.kkasztel.sensorstats.model.SensorResult
import com.kkasztel.sensorstats.utils.MapUtils.{adjust, combine}

object DataProcessor {

  def processSource(input: Iterator[String]): Map[String, SensorResult] =
    input
      .flatMap(HumidityRecordParser.parse)
      .map(r => SensorResult(r.sensor, r.value))
      .foldLeft(Map.empty[String, SensorResult]) {
        (m, r) => adjust(m, r.sensor, SensorResult.empty(r.sensor))(v => r.combine(v))
      }

  def foldResults(input: Iterator[Map[String, SensorResult]]): (List[SensorResult], Int) = {
    val combined = input.foldLeft((Map.empty[String, SensorResult], 0)) {
      (a, b) => (combine(a._1, b)(SensorResult.empty)((x, y) => x.combine(y)), a._2 + 1)
    }
    (combined._1.values.toList, combined._2)
  }
}
