package com.kkasztel.sensorstats.model

case class SensorResult(sensor: String, min: HumidityValue, max: HumidityValue, sum: Int, count: Int, errors: Int) extends Comparable[SensorResult] {

  def combine(o: SensorResult): SensorResult =
    SensorResult(sensor, min.min(o.min), max.max(o.max), sum + o.sum, count + o.count, errors + o.errors)

  def avg(): HumidityValue =
    if (count != errors) Humidity(sum / (count - errors))
    else Nan

  override def compareTo(o: SensorResult): Int = this.avg().compareTo(o.avg())

  override def toString: String = sensor + "," + min + "," + avg() + "," + max
}

object SensorResult {

  def apply(sensor: String, value: HumidityValue): SensorResult = value match {
    case v: Humidity => SensorResult(sensor, v, v, v.value, 1, 0)
    case _ => SensorResult(sensor, value, value, 0, 1, 1)
  }

  def empty(sensor: String): SensorResult = SensorResult(sensor, Nan, Nan, 0, 0, 0)
}
