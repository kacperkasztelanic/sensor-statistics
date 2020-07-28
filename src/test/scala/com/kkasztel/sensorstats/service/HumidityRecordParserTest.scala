package com.kkasztel.sensorstats.service

import com.kkasztel.sensorstats.model.{Humidity, HumidityRecord, Nan}
import org.scalatest.flatspec.AnyFlatSpec

class HumidityRecordParserTest extends AnyFlatSpec {

  "HumidityRecordParser" should "return Some(HumidityRecord) parsed from correctly formatted String" in {
    assertResult(Some(HumidityRecord("s1", Humidity(50))))(HumidityRecordParser.parse("s1,50"))
    assertResult(Some(HumidityRecord("s2", Nan)))(HumidityRecordParser.parse("s2,Nan"))
    assertResult(Some(HumidityRecord("s1", Humidity(50))))(HumidityRecordParser.parse(" s1 , 50 "))
  }

  it should "return None when unexpected format provided" in {
    assertResult(None)(HumidityRecordParser.parse("abc"))
    assertResult(None)(HumidityRecordParser.parse(" , "))
    assertResult(None)(HumidityRecordParser.parse(" ,50"))
  }

  it should "return None when null or empty String is provided" in {
    assertResult(None)(HumidityRecordParser.parse(null))
    assertResult(None)(HumidityRecordParser.parse(""))
  }
}
