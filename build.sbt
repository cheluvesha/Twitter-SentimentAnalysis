name := "SentimentAnalysis"

version := "0.1"

scalaVersion := "2.12.10"

coverageEnabled := true

scapegoatVersion in ThisBuild := "1.3.8"

libraryDependencies += "org.apache.spark" %% "spark-core" % "3.0.0"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.0.0"

libraryDependencies += "org.apache.spark" %% "spark-mllib" % "3.0.0"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "3.0.0"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.4.1"

libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.0"

libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.0.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % Test

libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "3.2.1"

libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % "3.2.1"

libraryDependencies += "jp.co.bizreach" %% "aws-s3-scala" % "0.0.15"
