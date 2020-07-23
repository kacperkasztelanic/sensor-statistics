package com.kkasztel.sensorstats

import com.kkasztel.sensorstats.model.Summary
import com.kkasztel.sensorstats.service.Reader

object Main {

  def main(args: Array[String]): Unit = {
    println(Summary(Reader.read(args(0)), 2))
  }
}
