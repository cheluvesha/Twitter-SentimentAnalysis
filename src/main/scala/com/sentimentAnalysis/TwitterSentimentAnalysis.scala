package com.sentimentAnalysis

import org.apache.log4j.Logger
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, SparkSession}

class TwitterSentimentAnalysis(sparkSession: SparkSession) {
  val logger: Logger = Logger.getLogger(getClass.getName)

  /** *
    * Reads data from the Kafka Topic
    * @param broker String
    * @param topic  String
    * @return DataFrame
    */
  def readDataFromKafka(broker: String, topic: String): DataFrame = {
    logger.info("Reading data from Kafka Topic")
    try {
      val kafkaDF = sparkSession.readStream
        .format("kafka")
        .option("kafka.bootstrap.servers", broker)
        .option("subscribe", topic)
        .option("startingOffsets", "earliest")
        .load()
      kafkaDF
    } catch {
      case nullPointerException: NullPointerException =>
        logger.error(nullPointerException.printStackTrace())
        throw new Exception("Passed Fields Are Null")
    }
  }

  /** *
    * Extracts Schema From Twitter Sample Json File
    * @param filePath String
    * @return StructType
    */
  def extractSchemaFromTwitterData(filePath: String): StructType = {
    logger.info("Extracting Schema From Twitter Json File")
    try {
      val twitterData = sparkSession.read
        .json(filePath)
        .toDF()
      twitterData.schema
    } catch {
      case nullPointerException: NullPointerException =>
        logger.error(nullPointerException.printStackTrace())
        throw new Exception("Can not create a Path from a null string")
      case fileNotFoundException: org.apache.spark.sql.AnalysisException =>
        logger.error(fileNotFoundException.printStackTrace())
        throw new Exception("Twitter Sample file not exist")
    }
  }

}
