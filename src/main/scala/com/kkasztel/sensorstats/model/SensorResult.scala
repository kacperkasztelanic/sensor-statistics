package com.kkasztel.sensorstats.model

sealed trait SensorResult extends Ordered[SensorResult] {

  def avg(): HumidityValue

  def sum: Int

  def count: Int

  def errors: Int

  def min: HumidityValue

  def max: HumidityValue

  def sensor: String

  def combine(o: SensorResult): SensorResult = (this, o) match {
    case (a: NonEmptyResult, b: NonEmptyResult) => NonEmptyResult(
      a.sensor,
      a.min.min(b.min),
      a.max.max(b.max),
      a.sum + b.sum,
      a.count + b.count,
      a.errors + b.errors
    )
    case (a: NonEmptyResult, EmptyResult) => a
    case (EmptyResult, b: NonEmptyResult) => b
    case _ => EmptyResult
  }

  override def compare(o: SensorResult): Int = this.avg().compareTo(o.avg())
}

case class NonEmptyResult(sensor: String, min: HumidityValue, max: HumidityValue, sum: Int, count: Int, errors: Int) extends SensorResult {

  override def avg(): HumidityValue =
    if (count != errors) Humidity(sum / (count - errors))
    else Nan

  override def toString: String = sensor + "," + min + "," + avg() + "," + max
}

case object EmptyResult extends SensorResult {

  override def avg(): HumidityValue = Nan

  override def count: Int = 0

  override def errors: Int = 0

  override def sum: Int = 0

  override def min: HumidityValue = Nan

  override def max: HumidityValue = Nan

  override def sensor: String = "empty"
}

object NonEmptyResult {

  def apply(sensor: String, value: HumidityValue): NonEmptyResult = value match {
    case v: Humidity => NonEmptyResult(sensor, v, v, v.value, 1, 0)
    case _ => NonEmptyResult(sensor, value, value, 0, 1, 1)
  }
}
