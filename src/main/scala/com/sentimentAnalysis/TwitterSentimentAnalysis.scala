package com.sentimentAnalysis

import org.apache.log4j.Logger
import org.apache.spark.ml.PipelineModel
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, SparkSession}

/***
  * Class which cleans tweet raw data in order to apply ML model on data to predict sentiment
  * @param sparkSession SparkSession
  */
class TwitterSentimentAnalysis(sparkSession: SparkSession) {
  val logger: Logger = Logger.getLogger(getClass.getName)
  sparkSession.udf.register("removeWords", remove)

  /** *
    * Reads data from the Kafka Topic
    * @param broker String - Kafka broker URL
    * @param topic  String - Kafka topic name
    * @return DataFrame - DataFrame which is created by reading data from kafka
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
        throw new Exception("Passed fields are null")
    }
  }

  /** *
    * Extracts Schema From Twitter Sample Json File
    * @param filePath String - Twitter sample json data
    * @return StructType - Schema
    */
  def extractSchemaFromTwitterData(filePath: String): StructType = {
    logger.info("Extracting schema from twitter Json file")
    try {
      val twitterData = sparkSession.read
        .json(filePath)
        .toDF()
      twitterData.schema
    } catch {
      case nullPointerException: NullPointerException =>
        logger.error(nullPointerException.printStackTrace())
        throw new Exception("Can not create a path from a null string")
      case fileNotFoundException: org.apache.spark.sql.AnalysisException =>
        logger.error(fileNotFoundException.printStackTrace())
        throw new Exception("Twitter sample file is not exist")
    }
  }

  /** *
    * Casting, Applying  Schema and Selecting Required Columns From The Kafka DataFrame
    * @param kafkaDF DataFrame - DataFrame which is created by reading data from kafka
    * @param schema  StructType - Custom created schema for twitter data
    * @return DataFrame - Processed DataFrame
    */
  def processKafkaDataFrame(
      kafkaDF: DataFrame,
      schema: StructType
  ): DataFrame = {
    logger.info("Processing the Kafka DataFrame")
    try {
      val twitterStreamDF = kafkaDF
        .selectExpr("CAST(value AS STRING) as jsonData")
        .select(from_json(col("jsonData"), schema).as("data"))
        .select(col("data.retweeted_status") as "tweet")
      val tweetDF =
        twitterStreamDF.select(col("tweet.text") as "tweet_text")
      val tweetNonNullDF = tweetDF.na.drop("any")
      tweetNonNullDF
    } catch {
      case sparkAnalysisException: org.apache.spark.sql.AnalysisException =>
        logger.error(sparkAnalysisException.printStackTrace())
        throw new Exception("Unable to execute a query")
    }
  }

  /** *
    * UDF for removing unwanted words from hashtag field
    * @return String
    */
  def remove: String => String =
    (words: String) => {
      var removeText: String = null
      if (words != null) {
        /* Remove # from hashtags, emoji's, hyperlinks, and twitter tags. Replace @mention with empty string,
          Special characters and Numbers
         */
        removeText = words
          .replaceAll("""(\b\w*RT)|[^a-zA-Z0-9\s\.\,\!,\@]""", "")
          .replaceAll("(http\\S+)", "")
          .replaceAll("(@\\w+)", "")
          .replaceAll("[^a-zA-z]", " ")
          .replaceAll("\\s{2,}", " ")

      } else {
        removeText = "nothing"
      }
      removeText
    }

  /** *
    * Removing the unwanted words from tweet_text field by applying UDF
    * @param tweetTextDF DataFrame - Tweet raw text DataFrame
    * @return DataFrame
    */
  def removeUnwantedWords(tweetTextDF: DataFrame): DataFrame = {
    logger.info("Removing the unwanted words from tweet field")
    try {
      tweetTextDF.createOrReplaceTempView("remove_words")
      val removedWordsDF = sparkSession.sql(
        """select removeWords(tweet_text) as tweet from remove_words"""
      )

      removedWordsDF.where("tweet != 'nothing'")
    } catch {
      case sparkAnalysisException: org.apache.spark.sql.AnalysisException =>
        logger.error(sparkAnalysisException.printStackTrace())
        throw new Exception("Unable to Execute Query")
    }
  }

  /***
    * Loading the model from the file and Applying it on DataFrame to predict the sentiment
    * @param cleanedDF DataFrame - Preprocessed DataFrame
    * @param modelFilePath String - ML Model file path
    * @return DataFrame
    */
  def applyModelAndPredictTheSentiment(
      cleanedDF: DataFrame,
      modelFilePath: String
  ): DataFrame = {
    try {
      logger.info("Loading the model and predicting the sentiment")
      val model = PipelineModel.load(modelFilePath)
      val predictedDF = model.transform(cleanedDF).select("tweet", "prediction")
      predictedDF
    } catch {
      case sqlAnalysisException: org.apache.spark.sql.AnalysisException =>
        logger.error(sqlAnalysisException.printStackTrace())
        throw new Exception("Unable to execute a query")
      case invalidInputException: org.apache.hadoop.mapred.InvalidInputException =>
        logger.error(invalidInputException.printStackTrace())
        throw new Exception("Model file path is not exist")
    }
  }

  /***
    * Writing the stream of DataFrame to CSV file
    * @param predictedDF DataFrame - Prediction column contained DataFrame
    * @param outputPath String - Output file path
    */
  def writeStreamDataFrame(predictedDF: DataFrame, outputPath: String): Unit = {
    try {
      logger.info("Performing writeStream operation on DataFrame")
      predictedDF.writeStream
        .format("csv")
        .option("path", outputPath)
        .outputMode("append")
        .option("checkpointLocation", "chk-point-dir")
        .trigger(Trigger.ProcessingTime("10 seconds"))
        .start()
        .awaitTermination()
    } catch {
      case nullPointerException: NullPointerException =>
        logger.error(nullPointerException.printStackTrace())
        throw new Exception("Null fields passed for output file path")
    }
  }
}
