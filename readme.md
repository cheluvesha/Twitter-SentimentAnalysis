## Project Title - Sentiment Analysis with Spark structured streaming and with kafka integration.

## Project Description 

### Reading twitter data which is published into kafka topic from twitter streaming,
### applying the transformations to perform data cleansing and applying the trained model on preprocessed data
### for predicting a sentiment for the tweet text.

## Dependencies required:

Spark-Core - 3.0.0 - Base of Spark application project,
Spark-Sql - 3.0.0 - Module for structured data processing,
Spark-Streaming - 3.0.0 - Allows to perform Streaming data processing,
scalatest - 3.0.1 - scala test,
Spark-streaming-kafka-0-10 - 3.0.0 - Helps to integrate spark streaming with kafka,
Spark-sql-kafka-0-10 - 3.0.0 - Source For Structured Streaming,
spark-mllib - 3.0.0 -  Spark Machine learning library,
kafka-clients - 2.4.1 - Kafka connector.


## How To 

    First Clone this https://github.com/cheluvesha/Twitter_Streaming_Twitter4j.git
    repositary which performs the streaming of twitter data and publish that data into kafka.
    
    After that clone this repositary,
    to create jar make use of sbt and use this sbt package command to create a jar, 
    after creating the jar submit your application with spark-submit,
    to run test cases use "sbt test" command.
    
    jar file which takes 3 arguments as an input
    args(0) - broker url
    args(1) - topic name
    args(2) - sample json file for schema
    args(3) - Model file path
    args(4) - S3 path
    
     
