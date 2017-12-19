package com.ping4.utils

import java.util.{StringTokenizer, Date, TimeZone}
import java.text.SimpleDateFormat
import java.sql.Timestamp

import com.datastax.driver.core.utils.UUIDs

object TimeUtils {
  val timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
  timestampFormat.setTimeZone(TimeZone.getTimeZone("UTC"))

  // returns Timestamp in UTC
  def now: Timestamp = {
    val timestampString = timestampFormat.format(new Date)
    Timestamp.valueOf(timestampString)
  }

  def millisecondsFromNow(fromNow : Long): Timestamp = {
    // gets string representation of current datetime
    val timestampString = timestampFormat.format(new Date().getTime() + fromNow)
    Timestamp.valueOf(timestampString)
  }

  def toCloudSearchDate(time: Timestamp): String = {
    val csTimestampFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    csTimestampFormat.format(time)
  }

  def fromCloudSearchDate(time: String): Timestamp = {
    Timestamp.valueOf(time.replaceAll("[TZ]", " "))
  }

  def lexicographicTimeUUID : String = {
    val originalTimeUUID = UUIDs.timeBased.toString
    val tokens = new StringTokenizer(originalTimeUUID, "-")
    tokens.countTokens() == 5 match {
      case true =>
        val time_low = tokens.nextToken()
        val time_mid = tokens.nextToken()
        val time_high_and_version = tokens.nextToken()
        val variant_and_sequence = tokens.nextToken()
        val node = tokens.nextToken()

        time_high_and_version + '-' + time_mid + '-' + time_low + '-' + variant_and_sequence + '-' + node;
      case false => originalTimeUUID
    }
  }
}
