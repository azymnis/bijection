/*
Copyright 2012 Twitter, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.twitter.bijection

import java.lang.{
  Short => JShort,
  Integer => JInt,
  Long => JLong,
  Float => JFloat,
  Double => JDouble,
  Byte => JByte
}
import java.nio.ByteBuffer
import java.util.UUID

import Bijection.build

trait NumericBijections {
  /**
   * Bijections between the numeric types and their java versions.
   */
  implicit val byte2Boxed: Bijection[Byte, JByte] =
    new Bijection[Byte, JByte] {
      def apply(b: Byte) = JByte.valueOf(b)
      override def invert(b: JByte) = b.byteValue
    }

  implicit val short2Boxed: Bijection[Short, JShort] =
    new Bijection[Short, JShort] {
      def apply(s: Short) = JShort.valueOf(s)
      override def invert(s: JShort) = s.shortValue
    }

  implicit val int2Boxed: Bijection[Int, JInt] =
    new Bijection[Int, JInt] {
      def apply(i: Int) = JInt.valueOf(i)
      override def invert(i: JInt) =  i.intValue
    }

  implicit val long2Boxed: Bijection[Long, JLong] =
    new Bijection[Long, JLong] {
      def apply(l: Long) = JLong.valueOf(l)
      override def invert(l: JLong) = l.longValue
    }

  implicit val float2Boxed: Bijection[Float, JFloat] =
    new Bijection[Float, JFloat] {
      def apply(f: Float) = JFloat.valueOf(f)
      override def invert(f: JFloat) = f.floatValue
    }

  implicit val double2Boxed: Bijection[Double, JDouble] =
    new Bijection[Double, JDouble] {
      def apply(d: Double) = JDouble.valueOf(d)
      override def invert(d: JDouble) = d.doubleValue
    }

  /**
   * Bijections between the numeric types and string.
   */
  implicit val byte2String: Bijection[Byte, String @@ Rep[Byte]] =
    new Bijection[Byte, String @@ Rep[Byte]] {
      def apply(b: Byte) = Tag(b.toString)
      override def invert(s: String @@ Rep[Byte]) = s.toByte
    }
  implicit val jbyte2String: Bijection[JByte, String @@ Rep[Byte]] =
    build[JByte, String @@ Rep[Byte]] { (b: JByte) => Tag[String,Rep[Byte]](b.toString) } { s =>
      JByte.valueOf(s) }

  implicit val short2String: Bijection[Short, String @@ Rep[Short]] =
    new Bijection[Short, String @@ Rep[Short]] {
      def apply(s: Short) = Tag(s.toString)
      override def invert(s: String @@ Rep[Short]) = s.toShort
    }
  implicit val jshort2String: Bijection[JShort, String @@ Rep[Short]] =
    build[JShort, String @@ Rep[Short]] { (b: JShort) => Tag[String,Rep[Short]](b.toString) } { s =>
      JShort.valueOf(s) }

  implicit val int2String: Bijection[Int, String @@ Rep[Int]] =
    new Bijection[Int, String @@ Rep[Int]] {
      def apply(i: Int) = Tag(i.toString)
      override def invert(s: String @@ Rep[Int]) = s.toInt
    }
  implicit val jint2String: Bijection[JInt, String @@ Rep[Int]] =
    build[JInt, String @@ Rep[Int]] { (b: JInt) => Tag[String,Rep[Int]](b.toString) } { s =>
      JInt.valueOf(s) }

  implicit val long2String: Bijection[Long, String @@ Rep[Long]] =
    new Bijection[Long, String @@ Rep[Long]] {
      def apply(l: Long) = Tag(l.toString)
      override def invert(s: String @@ Rep[Long]) = s.toLong
    }
  implicit val jlong2String: Bijection[JLong, String @@ Rep[Long]] =
    build[JLong, String @@ Rep[Long]] { (b: JLong) => Tag[String,Rep[Long]](b.toString) } { s =>
      JLong.valueOf(s) }

  implicit val float2String: Bijection[Float, String @@ Rep[Float]] =
    new Bijection[Float, String @@ Rep[Float]] {
      def apply(f: Float) = Tag(f.toString)
      override def invert(s: String @@ Rep[Float]) = s.toFloat
    }

  implicit val jfloat2String: Bijection[JFloat, String @@ Rep[Float]] =
    build[JFloat, String @@ Rep[Float]] { (b: JFloat) => Tag[String,Rep[Float]](b.toString) } { s =>
      JFloat.valueOf(s) }

  implicit val double2String: Bijection[Double, String @@ Rep[Double]] =
    new Bijection[Double, String @@ Rep[Double]] {
      def apply(d: Double) = Tag(d.toString)
      override def invert(s: String @@ Rep[Double]) = s.toDouble
    }

  implicit val jdouble2String: Bijection[JDouble, String @@ Rep[Double]] =
    build[JDouble, String @@ Rep[Double]] { (b: JDouble) => Tag[String,Rep[Double]](b.toString) } { s =>
      JDouble.valueOf(s) }
  /**
   * Bijections between the numeric types and Array[Byte].
   */
  val float2IntIEEE754: Bijection[Float, Int] =
    new Bijection[Float, Int] {
      def apply(f: Float) = JFloat.floatToIntBits(f)
      override def invert(i: Int) = JFloat.intBitsToFloat(i)
    }

  val double2LongIEEE754: Bijection[Double, Long] =
    new Bijection[Double, Long] {
      def apply(d: Double) = JDouble.doubleToLongBits(d)
      override def invert(l: Long) = JDouble.longBitsToDouble(l)
    }

  implicit val short2BigEndian: Bijection[Short, Array[Byte]] =
    new Bijection[Short, Array[Byte]] {
      def apply(value: Short) = {
        val buf = ByteBuffer.allocate(2)
        buf.putShort(value)
        buf.array
      }
      override def invert(b: Array[Byte]) = ByteBuffer.wrap(b).getShort
    }

  implicit val int2BigEndian: Bijection[Int, Array[Byte]] =
    new Bijection[Int, Array[Byte]] { value =>
      def apply(value: Int) = {
        val buf = ByteBuffer.allocate(4)
        buf.putInt(value)
        buf.array
      }
      override def invert(b: Array[Byte]) = ByteBuffer.wrap(b).getInt
    }

  implicit val long2BigEndian: Bijection[Long, Array[Byte]] =
    new Bijection[Long, Array[Byte]] {
      def apply(value: Long) = {
        val buf = ByteBuffer.allocate(8)
        buf.putLong(value)
        buf.array
      }
      override def invert(b: Array[Byte]) = ByteBuffer.wrap(b).getLong
    }

  implicit val float2BigEndian: Bijection[Float, Array[Byte]] =
    float2IntIEEE754 andThen int2BigEndian
  implicit val double2BigEndian: Bijection[Double, Array[Byte]] =
    double2LongIEEE754 andThen long2BigEndian

  /* Other types to and from Numeric types */
  implicit val uid2LongLong: Bijection[UUID, (Long,Long)] =
    new Bijection[UUID, (Long,Long)] { uid =>
      def apply(uid: UUID) =
        (uid.getMostSignificantBits, uid.getLeastSignificantBits)
      override def invert(ml: (Long,Long)) = new UUID(ml._1, ml._2)
    }

  implicit val date2Long: Bijection[java.util.Date, Long] =
    new Bijection[java.util.Date, Long] {
      def apply(d: java.util.Date) = d.getTime
      override def invert(l: Long) = new java.util.Date(l)
    }
}
