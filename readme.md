## Project Title - Sentiment Analysis with Spark structured streaming with kafka integration.

## Project Description 

### Reading twitter data which is published into kafka topic from twitter streaming,
### applying the transformations to perform data cleansing and applying the trained model on preprocessed data
### for predicting a sentiment for the tweet text.

## Dependencies required:

Spark-Core - 3.0.0,
Spark-Sql - 3.0.0,
Spark-Streaming - 3.0.0,
scalatest - 3.0.1,
Spark-streaming-kafka-0-10 - 3.0.0,
Spark-sql-kafka-0-10 - 3.0.0,
spark-mllib - 3.0.0,
kafka-clients - 2.4.1

## Plugins Used

sbt-scapegoat - 1.1.0,
Assembly - 0.15.0,
sbt-scoverage - 1.6.1

## How To 

    First Clone this https://github.com/cheluvesha/Twitter_Streaming_Twitter4j.git
    repositary which performs the streaming of twitter data and publish that data into kafka.
    
    After that clone this repositary,
    to create jar make use of sbt and use this sbt assembly command to create fat jar, 
    sbt assembly which will include all dependencies into jar file but spark dependencies has 
    to be in provided scope (spark dependencies are no need to include in the jar files)

    after creating the jar submit your application with spark-submit,
    to run test cases use "sbt test" command,
    to generate scapegoat report use "sbt scapegoat" and
    to generate scoverage report use "sbt coverageReport"  
    
    jar file which takes 3 arguments as an input
    args(0) - sampleJsonFile path 
    args(1) - modelFilePath
    args(2) - outputPath
     