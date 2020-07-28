package com.kkasztel.sensorstats.model

import org.scalacheck.Gen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class HumidityValueTest extends AnyFlatSpec with ScalaCheckDrivenPropertyChecks with Matchers {

  val intGen: Gen[Int] = Gen.choose(-100, 200)
  val humidityValueGen: Gen[(HumidityValue, HumidityValue)] =
    intGen.flatMap(i => intGen.map(j => (HumidityValue(i.toString), HumidityValue(j.toString))))

  "HumidityValue" should "return Humidity(d) or Nan when Int is provided" in {
    forAll(intGen) { (d: Int) =>
      val humidity = HumidityValue(d.toString)
      if (d < 1 || d > 100) {
        humidity shouldBe Nan
      }
      else {
        humidity should equal(Humidity(d))
      }
    }
  }

  it should "return Humidity(s.toInt) or Nan when String is provided" in {
    forAll(Gen.alphaNumStr) { (s: String) =>
      val humidity = HumidityValue(s)
      s match {
        case Int(v) =>
          if (v < 1 || v > 100) humidity shouldBe Nan
          else humidity should equal(Humidity(v))
        case _ => Nan
      }
    }
  }

  it should "return correct result when compared to another one" in {
    forAll(humidityValueGen) { p: (HumidityValue, HumidityValue) =>
      (p._1, p._2) match {
        case (v1: Humidity, v2: Humidity) =>
          if (v1.value > v2.value) v1.compare(v2) should be > 0
          else if (v1.value < v2.value) v1.compare(v2) should be < 0
          else v1.compare(v2) shouldBe 0
        case (v1: Humidity, Nan) => v1.compare(Nan) should be > 0
        case (Nan, v2: Humidity) => Nan.compare(v2) should be < 0
        case (Nan, Nan) => Nan.compare(Nan) shouldBe 0
      }
    }
  }

  it should "return greater value when compared to another one" in {
    forAll(humidityValueGen) { p: (HumidityValue, HumidityValue) =>
      (p._1, p._2) match {
        case (v1: Humidity, v2: Humidity) =>
          if (v1.value > v2.value) v1.max(v2) shouldBe v1
          else if (v1.value < v2.value) v1.max(v2) shouldBe v2
          else v1.max(v2) shouldBe v1
        case (v1: Humidity, Nan) => v1.max(Nan) shouldBe v1
        case (Nan, v2: Humidity) => Nan.max(v2) shouldBe v2
        case (Nan, Nan) => Nan.max(Nan) shouldBe Nan
      }
    }
  }

  it should "return lesser value when compared to another one" in {
    forAll(humidityValueGen) { p: (HumidityValue, HumidityValue) =>
      (p._1, p._2) match {
        case (v1: Humidity, v2: Humidity) =>
          if (v1.value > v2.value) v1.min(v2) shouldBe v2
          else if (v1.value < v2.value) v1.min(v2) shouldBe v1
          else v1.min(v2) shouldBe v1
        case (v1: Humidity, Nan) => v1.min(Nan) shouldBe v1
        case (Nan, v2: Humidity) => Nan.min(v2) shouldBe v2
        case (Nan, Nan) => Nan.min(Nan) shouldBe Nan
      }
    }
  }
}
