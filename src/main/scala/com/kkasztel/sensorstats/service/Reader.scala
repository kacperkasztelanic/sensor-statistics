package com.kkasztel.sensorstats.service

import java.io.File

import com.kkasztel.sensorstats.model.SensorResult
import com.kkasztel.sensorstats.utils.MapUtils.{adjust, combine}

import scala.io.Source
import scala.util.Using

object Reader {

  def read(path: String): Seq[SensorResult] = {
    getListOfFiles(new File(path))
      .flatMap(f =>
        Using(Source.fromFile(f)) { s =>
          s.getLines()
            .drop(1)
            .flatMap(HumidityRecordParser.parse)
            .map(r => SensorResult(r.sensor, r.value))
            .foldLeft(Map[String, SensorResult]())((m, r) => adjust(m, r.sensor, SensorResult.empty(r.sensor))(v => r.combine(v)))
        }.toOption
      )
      .foldLeft(Map[String, SensorResult]())((a, b) => combine(a, b)(SensorResult.empty)((x, y) => x.combine(y)))
      .values
      .toList
      .sorted
      .reverse
  }

  def getListOfFiles(dir: File): Iterator[File] = dir.listFiles.filter(_.isFile).iterator
}
