package com.kkasztel.sensorstats.service

import java.io.File

import com.kkasztel.sensorstats.model.SensorResult

import scala.io.Source
import scala.util.Using

object Reader {

  private val rowsToDrop = 1

  def read(path: String,
           single: Iterator[String] => Map[String, SensorResult],
           fold: Iterator[Map[String, SensorResult]] => (List[SensorResult], Int)
          ): (List[SensorResult], Int) = {
    val parts = getListOfFiles(new File(path))
      .flatMap(
        f => Using(Source.fromFile(f)) {
          s => single(s.getLines().drop(rowsToDrop))
        }.toOption
      )
    fold(parts)
  }

  private def getListOfFiles(dir: File): Iterator[File] =
    Option(dir.listFiles)
      .iterator
      .flatten
      .filter(_.isFile)
}
