## Dependencies required:

Spark-Core - 3.0.0 - Base of Spark application project,
Spark-Sql - 3.0.0 - Module for structured data processing,
Spark-Streaming - 3.0.0 - Allows to perform Streaming data processing,
scalatest - 3.0.1 - scala test,
Spark-streaming-kafka-0-10 - 3.0.0 - Helps to integrate spark streaming with kafka,
Spark-sql-kafka-0-10 - 3.0.0 - Source For Structured Streaming,
spark-mllib - 3.0.0 -  Spark Machine learning library,
hadoop-common - 3.2.1 - collection of common utilities and libraries that support other Hadoop modules
hadoop-aws - 3.2.1 - Hadoop and AWS module provides support for AWS integration
aws-s3-scala - 0.0.15 - Provides AWS SDK
kafka-clients - 2.4.1 - Kafka connector.

## Plugins Used

sbt-scapegoat - 1.1.0 - static code analyser,
Assembly - 0.15.0 - used to create a fat jar,
sbt-scoverage - 1.6.1 - code coverage tool

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
    args(0) - broker url
    args(1) - topic name
    args(2) - sample json file for schema
    args(3) - Model file path
    args(4) - S3 path
    
     
