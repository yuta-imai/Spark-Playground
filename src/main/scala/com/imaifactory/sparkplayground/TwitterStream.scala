package com.imaifactory.sparkplayground

/**
  * Created by yimai on 2016/05/21.
  */

import com.typesafe.config.ConfigFactory
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter._


case class Tweet(createdAt:Long, text:String)
object TwitterStream {
  def main(args: Array[String]) {

    val prop = ConfigFactory.load()
    System.setProperty("twitter4j.oauth.consumerKey", prop.getString("consumerKey"))
    System.setProperty("twitter4j.oauth.consumerSecret", prop.getString("consumerSecret"))
    System.setProperty("twitter4j.oauth.accessToken", prop.getString("accessToken"))
    System.setProperty("twitter4j.oauth.accessTokenSecret", prop.getString("accessTokenSecret"))

    val conf = new SparkConf().setAppName(TwitterUtils.getClass.toString).setMaster("local[8]")
    conf.set("spark.driver.allowMultipleContexts","true")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(conf, Seconds(2))
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._
    val tweets = TwitterUtils.createStream(ssc, None,Array("amazon"))
    val twt = tweets.window(Seconds(60))

    twt
      .map(tweet => {
        Tweet(tweet.getCreatedAt.getTime/100, tweet.getText)})
      .foreachRDD(rdd => {
        val ds = rdd.toDS
        ds.groupBy(_.createdAt).count().show
      })

    ssc.start()
    ssc.awaitTermination()
  }
}
