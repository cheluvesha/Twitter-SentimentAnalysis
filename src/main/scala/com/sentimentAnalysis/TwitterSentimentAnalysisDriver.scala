package com.sentimentAnalysis

import com.utilities.{AWSConfiguration, Utility}
import org.apache.spark.sql.{DataFrame, SparkSession}

/***
  * Driver class for TwitterSentimentAnalysis
  */
object TwitterSentimentAnalysisDriver {
  val sparkSession: SparkSession =
    Utility.createSparkSessionObj("Twitter-SentimentAnalysis")
  val twitterSentimentAnalysis = new TwitterSentimentAnalysis(sparkSession)

  /***
    * Calls respective methods to get tweetRawDF
    * @param broker String - Kafka Broker
    * @param topic String - Kafka topic
    * @param sampleJsonFile String - Twitter sample json file
    * @return DataFrame
    */
  def readExtractAndProcessKafkaData(
      broker: String,
      topic: String,
      sampleJsonFile: String
  ): DataFrame = {
    val kafkaDF = twitterSentimentAnalysis.readDataFromKafka(broker, topic)
    val schema =
      twitterSentimentAnalysis.extractSchemaFromTwitterData(sampleJsonFile)
    val tweetRawDF =
      twitterSentimentAnalysis.processKafkaDataFrame(kafkaDF, schema)
    val cleanedTweetDF =
      twitterSentimentAnalysis.removeUnwantedWords(tweetRawDF)
    cleanedTweetDF
  }

  // Entry point to an Application
  def main(args: Array[String]): Unit = {
    val broker = args(0)
    val topic = args(1)
    val sampleJsonFile = args(2)
    val modelFilePath = args(3)
    val outputPath = args(4)
    val awsAccessKey = System.getenv("AWS_ACCESS_KEY_ID")
    val awsSecretKey = System.getenv("AWS_SECRET_ACCESS_KEY")
    val cleanedTweetDF =
      readExtractAndProcessKafkaData(broker, topic, sampleJsonFile)
    val predictedDF =
      twitterSentimentAnalysis.applyModelAndPredictTheSentiment(
        cleanedTweetDF,
        modelFilePath
      )
    AWSConfiguration.connectToS3(
      sparkSession.sparkContext,
      awsAccessKey,
      awsSecretKey
    )
    twitterSentimentAnalysis.writeStreamDataFrame(
      predictedDF,
      outputPath
    )
  }
}
