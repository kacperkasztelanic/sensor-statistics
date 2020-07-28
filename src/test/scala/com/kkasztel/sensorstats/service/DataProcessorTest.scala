package com.kkasztel.sensorstats.service

import com.kkasztel.sensorstats.model.EmptyResult
import org.scalatest.flatspec.AnyFlatSpec

class DataProcessorTest extends AnyFlatSpec {

  "DataProcessor.processSource" should "return an empty map when empty iterator or non-parseable data is provided" in {
    assertResult(Map.empty)(DataProcessor.processSource(Iterator()))
    assertResult(Map.empty)(DataProcessor.processSource(Iterator(",")))
    assertResult(Map.empty)(DataProcessor.processSource(Iterator(", 50")))
  }

  it should "return a non-empty map with results of processing a single file with the number of keys equal to the number of distinct sensors" in {
    assertResult(2)(DataProcessor.processSource(Iterator("s1,50", "s1,40", "s2,Nan")).size)
  }

  "DataProcessor.foldResults" should "return combine result maps correctly" in {
    assertResult(1)(DataProcessor.foldResults(Iterator(Map("s1" -> EmptyResult)))._1.size)
    assertResult(2)(DataProcessor.foldResults(Iterator(Map("s1" -> EmptyResult, "s2" -> EmptyResult), Map("s1" -> EmptyResult)))._1.size)
  }

  it should "return correct number of processed sources" in {
    assertResult(0)(DataProcessor.foldResults(Iterator())._2)
    assertResult(1)(DataProcessor.foldResults(Iterator(Map()))._2)
    assertResult(2)(DataProcessor.foldResults(Iterator(Map(), Map()))._2)
  }
}
