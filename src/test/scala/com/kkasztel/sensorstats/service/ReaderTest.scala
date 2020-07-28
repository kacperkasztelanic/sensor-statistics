package com.kkasztel.sensorstats.service

import com.kkasztel.sensorstats.model.{Humidity, Nan, NonEmptyResult}
import org.scalatest.flatspec.AnyFlatSpec

class ReaderTest extends AnyFlatSpec {

  "Reader" should "process the example input correctly" in {
    assertResult((List(
      NonEmptyResult("s1", Humidity(10), Humidity(98), 108, 3, 1),
      NonEmptyResult("s2", Humidity(78), Humidity(88), 246, 3, 0),
      NonEmptyResult("s3", Nan, Nan, 0, 1, 1)
    ), 2)) {
      Reader.read("src/test/resources/sample", DataProcessor.processSource, DataProcessor.foldResults)
    }
  }

  it should "return empty result when folder not found" in {
    assertResult((List(), 0)) {
      Reader.read("sample2", DataProcessor.processSource, DataProcessor.foldResults)
    }
  }
}
