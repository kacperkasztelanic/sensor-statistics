package com.kkasztel.sensorstats.utils

object MapUtils {

  implicit class MapUtils[A, B](m: Map[A, B]) {

    def adjust(k: A, v: => B)(f: B => B): Map[A, B] = m.updated(k, f(m.getOrElse(k, v)))

    def combine(o: Map[A, B], d: A => B)(c: (B, B) => B): Map[A, B] = {
      val m1 = m.withDefault(d)
      val m2 = o.withDefault(d)
      m1.keySet
        .union(m2.keySet)
        .map(k => (k, c(m1(k), m2(k))))
        .toMap
    }
  }

}
