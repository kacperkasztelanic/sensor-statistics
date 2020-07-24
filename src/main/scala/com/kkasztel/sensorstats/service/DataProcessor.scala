package com.kkasztel.sensorstats.service

import com.kkasztel.sensorstats.model.SensorResult
import com.kkasztel.sensorstats.utils.MapUtils._

object DataProcessor {

  def processSource(input: Iterator[String]): Map[String, SensorResult] =
    input
      .flatMap(HumidityRecordParser.parse)
      .map(r => SensorResult(r.sensor, r.value))
      .foldLeft(Map.empty[String, SensorResult]) {
        (m, r) =>
          m.adjust(r.sensor, SensorResult.empty(r.sensor)) {
            v => r.combine(v)
          }
      }

  def foldResults(input: Iterator[Map[String, SensorResult]]): (List[SensorResult], Int) = {
    val combined = input.foldLeft((Map.empty[String, SensorResult], 0)) {
      (a, b) => {
        val c = a._1.combine(b, SensorResult.empty) {
          (x, y) => x.combine(y)
        }
        (c, a._2 + 1)
      }
    }
    (combined._1.values.toList, combined._2)
  }
}
