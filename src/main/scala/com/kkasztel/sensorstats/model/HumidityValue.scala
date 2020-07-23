package com.kkasztel.sensorstats.model

import scala.util.Try

sealed trait HumidityValue extends Comparable[HumidityValue] {

  def max(o: HumidityValue): HumidityValue = extreme(o, _.max(_))

  def min(o: HumidityValue): HumidityValue = extreme(o, _.min(_))

  private def extreme(o: HumidityValue, f: (Int, Int) => Int): HumidityValue = (this, o) match {
    case (a: Humidity, b: Humidity) => Humidity(f(a.value, b.value))
    case (a: Humidity, Nan) => a
    case (Nan, b: Humidity) => b
    case _ => Nan
  }

  override def compareTo(o: HumidityValue): Int = (this, o) match {
    case (a: Humidity, b: Humidity) => a.value.compareTo(b.value)
    case (_: Humidity, Nan) => 1
    case (Nan, _: Humidity) => -1
    case _ => 0
  }
}

case class Humidity(value: Int) extends HumidityValue {

  override def toString: String = value.toString
}

case object Nan extends HumidityValue {

  override def toString: String = "Nan"
}

object HumidityValue {

  val range = Range(1, 100)

  def apply(value: String): HumidityValue = value match {
    case Int(v) if range.contains(v) => Humidity(v)
    case _ => Nan
  }
}

object Int {

  def unapply(s: String): Option[Int] = Try(s.toInt).toOption
}