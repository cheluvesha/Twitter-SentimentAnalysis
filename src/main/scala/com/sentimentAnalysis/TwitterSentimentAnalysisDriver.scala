package com.sentimentAnalysis

import com.utilities.Utility
import org.apache.spark.sql.SparkSession

object TwitterSentimentAnalysisDriver {
  val sparkSession: SparkSession =
    Utility.createSparkSessionObj("Twitter-SentimentAnalysis")

  def main(args: Array[String]): Unit = {
    val broker = System.getenv("BROKER")
    val topic = System.getenv("TOPIC")
    val sampleJsonFile = "./Resources/twitterSchema.json"
    val twitterSentimentAnalysis = new TwitterSentimentAnalysis(sparkSession)
    val kafkaDF = twitterSentimentAnalysis.readDataFromKafka(broker, topic)
    val schema =
      twitterSentimentAnalysis.extractSchemaFromTwitterData(sampleJsonFile)
  }
}
