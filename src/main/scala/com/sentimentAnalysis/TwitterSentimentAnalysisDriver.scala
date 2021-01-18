package com.sentimentAnalysis

import com.utilities.Utility
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, SparkSession}

object TwitterSentimentAnalysisDriver {
  val sparkSession: SparkSession =
    Utility.createSparkSessionObj("Twitter-SentimentAnalysis")
  val twitterSentimentAnalysis = new TwitterSentimentAnalysis(sparkSession)

  /***
    * Calls respective methods to get tweetRawDF
    * @param broker String
    * @param topic String
    * @param sampleJsonFile String
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
    val broker = System.getenv("BROKER")
    val topic = System.getenv("TOPIC")
    val sampleJsonFile = "./Resources/twitterSchema.json"
    val cleanedTweetDF =
      readExtractAndProcessKafkaData(broker, topic, sampleJsonFile)
    cleanedTweetDF.writeStream
      .format("console")
      .outputMode("append")
      .option("checkpointLocation", "chk-point-dir")
      .trigger(Trigger.ProcessingTime("5 seconds"))
      .start()
      .awaitTermination()
  }
}
