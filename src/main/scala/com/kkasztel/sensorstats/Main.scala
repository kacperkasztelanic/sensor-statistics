package com.kkasztel.sensorstats

import com.kkasztel.sensorstats.model.Summary
import com.kkasztel.sensorstats.service.DataProcessor.{foldResults, processSource}
import com.kkasztel.sensorstats.service.Reader

object Main {

  def main(args: Array[String]): Unit = {
    Option(args)
      .filterNot(_.isEmpty)
      .foreach { a =>
        val res = Reader.read(a(0), processSource, foldResults)
        println(Summary(res._1, res._2))
      }
  }
}
