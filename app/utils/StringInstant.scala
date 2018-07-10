package utils

import java.util.Date
import java.text.{SimpleDateFormat, DateFormat}
import java.util.Calendar

object StringInstant {
  def convert[T <: String](date: T): Unit = {
    val formatter = new SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa").parse(date)
  }
}
