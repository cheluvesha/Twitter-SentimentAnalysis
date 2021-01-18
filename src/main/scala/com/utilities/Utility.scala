package com.utilities

import org.apache.spark.sql.SparkSession

/***
  * Utility Class is used to create commonly used functions such as creating SparkSession etc.
  */
object Utility {

  /***
    * Creates SparkSession Object
    * @param appName String
    * @return SparkSession
    */
  def createSparkSessionObj(appName: String): SparkSession = {
    val sparkSession = SparkSession
      .builder()
      .master("local[*]")
      .appName(appName)
      .getOrCreate()
    sparkSession
  }
}
