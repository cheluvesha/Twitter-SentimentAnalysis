package com.utilities

import org.apache.log4j.Logger
import org.apache.spark.SparkContext

/***
  * Class configures S3 file system with hadoop Configuration
  */
object AWSConfiguration {
  val logger: Logger = Logger.getLogger(getClass.getName)

  /***
    * configures hadoop with S3 file system
    * @param sparkContext SparkContext
    * @param awsAccessKey String -  AWS Access Key Id
    * @param awsSecretKey String - AWS Secret Access key
    * @return
    */
  def connectToS3(
      sparkContext: SparkContext,
      awsAccessKey: String,
      awsSecretKey: String
  ): Boolean = {
    try {
      logger.info("Started Configuring the AWS S3")
      System.setProperty("com.amazonaws.services.s3.enableV4", "true")
      sparkContext.hadoopConfiguration
        .set("fs.s3a.awsAccessKeyId", awsAccessKey)
      sparkContext.hadoopConfiguration
        .set(
          "fs.s3a.awsSecretAccessKey",
          awsSecretKey
        )
      sparkContext.hadoopConfiguration.set(
        "fs.s3a.impl",
        "org.apache.hadoop.fs.s3a.S3AFileSystem"
      )
      sparkContext.hadoopConfiguration
        .set("fs.s3a.endpoint", "s3.amazonaws.com")
      true
    } catch {
      case illegalArgException: IllegalArgumentException =>
        logger.error(illegalArgException.printStackTrace())
        throw new Exception("Hadoop AWS properties are not valid")
    }

  }
}
