#!/bin/sh
export BROKER=$KAFKA_BROKER
export TOPIC=$KAFKA_TOPIC
export SAMPLE_FILE=$TWEET_SAMPLE_FILE
export MODEL_FILE=$MODEL
export S3PATH=$PATH2SAVE
export AWS_ACCESS_KEY_ID=$AWSACCESSKEYID
export AWS_SECRET_ACCESS_KEY=$AWSSECRETACCESSKEY

spark-submit --driver-java-options "-Dlog4j.configuration=file:log4j.properties" --packages org.apache.spark:spark-streaming-kafka-0-10_2.12:3.0.0,org.apache.spark:spark-sql-kafka-0-10_2.12:3.0.0,jp.co.bizreach:aws-s3-scala_2.12:0.0.15,org.apache.hadoop:hadoop-common:3.2.0,org.apache.hadoop:hadoop-aws:3.2.0,com.amazonaws:aws-java-sdk-bundle:1.11.375 --class com.sentimentAnalysis.TwitterSentimentAnalysisDriver "./target/scala-2.12/sentimentanalysis_2.12-0.1.jar" $BROKER $TOPIC $SAMPLE_FILE $MODEL_FILE $S3PATH