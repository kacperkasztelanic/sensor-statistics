package com.kkasztel.sensorstats.utils

object MapUtils {

  def adjust[A, B](m: Map[A, B], k: A, v: => B)(f: B => B): Map[A, B] =
    m.updated(k, f(m.getOrElse(k, v)))

  def combine[A, B](a: Map[A, B], b: Map[A, B])(d: A => B)(c: (B, B) => B): Map[A, B] = {
    val a1 = a.withDefault(d)
    val b1 = b.withDefault(d)
    a1.keySet
      .union(b1.keySet)
      .map(k => (k, c(a1(k), b1(k))))
      .toMap
  }
}
