package com.twitterSentimentAnalysisTest

import com.sentimentAnalysis.TwitterSentimentAnalysis
import com.utilities.Utility
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class TwitterSentimentAnalysisTest extends FunSuite with BeforeAndAfterAll {
  var sparkSession: SparkSession = _
  var twitterSentimentAnalysis: TwitterSentimentAnalysis = _
  val sampleJsonFile = "./Resources/twitterSchema.json"
  val fileSchema =
    """StructType(StructField(contributors,StringType,true), StructField(coordinates,StringType,true), StructField(created_at,StringType,true), StructField(entities,StructType(StructField(hashtags,ArrayType(StringType,true),true), StructField(symbols,ArrayType(StringType,true),true), StructField(urls,ArrayType(StringType,true),true), StructField(user_mentions,ArrayType(StructType(StructField(id,LongType,true), StructField(id_str,StringType,true), StructField(indices,ArrayType(LongType,true),true), StructField(name,StringType,true), StructField(screen_name,StringType,true)),true),true)),true), StructField(favorite_count,LongType,true), StructField(favorited,BooleanType,true), StructField(filter_level,StringType,true), StructField(geo,StringType,true), StructField(id,LongType,true), StructField(id_str,StringType,true), StructField(in_reply_to_screen_name,StringType,true), StructField(in_reply_to_status_id,StringType,true), StructField(in_reply_to_status_id_str,StringType,true), StructField(in_reply_to_user_id,StringType,true), StructField(in_reply_to_user_id_str,StringType,true), StructField(is_quote_status,BooleanType,true), StructField(lang,StringType,true), StructField(place,StringType,true), StructField(quote_count,LongType,true), StructField(reply_count,LongType,true), StructField(retweet_count,LongType,true), StructField(retweeted,BooleanType,true), StructField(retweeted_status,StructType(StructField(contributors,StringType,true), StructField(coordinates,StringType,true), StructField(created_at,StringType,true), StructField(entities,StructType(StructField(hashtags,ArrayType(StringType,true),true), StructField(symbols,ArrayType(StringType,true),true), StructField(urls,ArrayType(StructType(StructField(display_url,StringType,true), StructField(expanded_url,StringType,true), StructField(indices,ArrayType(LongType,true),true), StructField(url,StringType,true)),true),true), StructField(user_mentions,ArrayType(StringType,true),true)),true), StructField(extended_tweet,StructType(StructField(display_text_range,ArrayType(LongType,true),true), StructField(entities,StructType(StructField(hashtags,ArrayType(StringType,true),true), StructField(symbols,ArrayType(StringType,true),true), StructField(urls,ArrayType(StructType(StructField(display_url,StringType,true), StructField(expanded_url,StringType,true), StructField(indices,ArrayType(LongType,true),true), StructField(url,StringType,true)),true),true), StructField(user_mentions,ArrayType(StringType,true),true)),true), StructField(full_text,StringType,true)),true), StructField(favorite_count,LongType,true), StructField(favorited,BooleanType,true), StructField(filter_level,StringType,true), StructField(geo,StringType,true), StructField(id,LongType,true), StructField(id_str,StringType,true), StructField(in_reply_to_screen_name,StringType,true), StructField(in_reply_to_status_id,StringType,true), StructField(in_reply_to_status_id_str,StringType,true), StructField(in_reply_to_user_id,StringType,true), StructField(in_reply_to_user_id_str,StringType,true), StructField(is_quote_status,BooleanType,true), StructField(lang,StringType,true), StructField(place,StringType,true), StructField(possibly_sensitive,BooleanType,true), StructField(quote_count,LongType,true), StructField(reply_count,LongType,true), StructField(retweet_count,LongType,true), StructField(retweeted,BooleanType,true), StructField(source,StringType,true), StructField(text,StringType,true), StructField(truncated,BooleanType,true), StructField(user,StructType(StructField(contributors_enabled,BooleanType,true), StructField(created_at,StringType,true), StructField(default_profile,BooleanType,true), StructField(default_profile_image,BooleanType,true), StructField(description,StringType,true), StructField(favourites_count,LongType,true), StructField(follow_request_sent,StringType,true), StructField(followers_count,LongType,true), StructField(following,StringType,true), StructField(friends_count,LongType,true), StructField(geo_enabled,BooleanType,true), StructField(id,LongType,true), StructField(id_str,StringType,true), StructField(is_translator,BooleanType,true), StructField(lang,StringType,true), StructField(listed_count,LongType,true), StructField(location,StringType,true), StructField(name,StringType,true), StructField(notifications,StringType,true), StructField(profile_background_color,StringType,true), StructField(profile_background_image_url,StringType,true), StructField(profile_background_image_url_https,StringType,true), StructField(profile_background_tile,BooleanType,true), StructField(profile_banner_url,StringType,true), StructField(profile_image_url,StringType,true), StructField(profile_image_url_https,StringType,true), StructField(profile_link_color,StringType,true), StructField(profile_sidebar_border_color,StringType,true), StructField(profile_sidebar_fill_color,StringType,true), StructField(profile_text_color,StringType,true), StructField(profile_use_background_image,BooleanType,true), StructField(protected,BooleanType,true), StructField(screen_name,StringType,true), StructField(statuses_count,LongType,true), StructField(time_zone,StringType,true), StructField(translator_type,StringType,true), StructField(url,StringType,true), StructField(utc_offset,StringType,true), StructField(verified,BooleanType,true)),true)),true), StructField(source,StringType,true), StructField(text,StringType,true), StructField(timestamp_ms,StringType,true), StructField(truncated,BooleanType,true), StructField(user,StructType(StructField(contributors_enabled,BooleanType,true), StructField(created_at,StringType,true), StructField(default_profile,BooleanType,true), StructField(default_profile_image,BooleanType,true), StructField(description,StringType,true), StructField(favourites_count,LongType,true), StructField(follow_request_sent,StringType,true), StructField(followers_count,LongType,true), StructField(following,StringType,true), StructField(friends_count,LongType,true), StructField(geo_enabled,BooleanType,true), StructField(id,LongType,true), StructField(id_str,StringType,true), StructField(is_translator,BooleanType,true), StructField(lang,StringType,true), StructField(listed_count,LongType,true), StructField(location,StringType,true), StructField(name,StringType,true), StructField(notifications,StringType,true), StructField(profile_background_color,StringType,true), StructField(profile_background_image_url,StringType,true), StructField(profile_background_image_url_https,StringType,true), StructField(profile_background_tile,BooleanType,true), StructField(profile_banner_url,StringType,true), StructField(profile_image_url,StringType,true), StructField(profile_image_url_https,StringType,true), StructField(profile_link_color,StringType,true), StructField(profile_sidebar_border_color,StringType,true), StructField(profile_sidebar_fill_color,StringType,true), StructField(profile_text_color,StringType,true), StructField(profile_use_background_image,BooleanType,true), StructField(protected,BooleanType,true), StructField(screen_name,StringType,true), StructField(statuses_count,LongType,true), StructField(time_zone,StringType,true), StructField(translator_type,StringType,true), StructField(url,StringType,true), StructField(utc_offset,StringType,true), StructField(verified,BooleanType,true)),true))"""
  var schema: StructType = _

  override def beforeAll(): Unit = {
    sparkSession = Utility.createSparkSessionObj("Test")
    twitterSentimentAnalysis = new TwitterSentimentAnalysis(sparkSession)
  }
  test("givenNullDataToReadDataFromKafkaMustTriggerNPE") {
    val thrown = intercept[Exception] {
      twitterSentimentAnalysis.readDataFromKafka(null, null)
    }
    assert(thrown.getMessage === "Passed Fields Are Null")
  }
  test("givenNullDataToReadDataFromKafkaMustTriggerNPEAndO/PMustNotEqual") {
    val thrown = intercept[Exception] {
      twitterSentimentAnalysis.readDataFromKafka(null, null)
    }
    assert(thrown.getMessage != "")
  }
  test("givenSampleFileItMustReadAndOutputMustEqualAsExpected") {
    schema =
      twitterSentimentAnalysis.extractSchemaFromTwitterData(sampleJsonFile)
    assert(schema.toString === fileSchema)
  }
  test("givenSampleFileItMustReadAndOutputMustNotEqualAsExpected") {
    val schema =
      twitterSentimentAnalysis.extractSchemaFromTwitterData(sampleJsonFile)
    assert(schema.toString != "")
  }
  test("givenNullFieldAsFilePathShouldThrowNPE") {
    val thrown = intercept[Exception] {
      twitterSentimentAnalysis.extractSchemaFromTwitterData(null)
    }
    assert(thrown.getMessage === "Can not create a Path from a null string")
  }
  test("givenWrongFieldAsFilePathShouldThrowFNE") {
    val thrown = intercept[Exception] {
      twitterSentimentAnalysis.extractSchemaFromTwitterData("NotExist")
    }
    assert(thrown.getMessage === "Twitter Sample file not exist")
  }
}
