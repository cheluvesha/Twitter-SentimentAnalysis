package com.utilities

import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession

/***
  * Utility Class is used to create commonly used functions such as creating SparkSession etc.
  */
object Utility {
  val logger: Logger = Logger.getLogger(getClass.getName)

  /***
    * Creates SparkSession Object
    * @param appName String
    * @return SparkSession
    */
  def createSparkSessionObj(appName: String): SparkSession = {
    try {
      logger.info(s"Creating a SparkSession for $appName")
      val sparkSession = SparkSession
        .builder()
        .master("local[*]")
        .config("spark.streaming.stopGracefullyOnShutdown", value = true)
        .appName(appName)
        .getOrCreate()
      sparkSession
    } catch {
      case nullPointerException: NullPointerException =>
        logger.error(nullPointerException.printStackTrace())
        throw new Exception("Null field is passed for application name")
    }
  }
}
