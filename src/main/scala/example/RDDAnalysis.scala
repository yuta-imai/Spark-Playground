package example

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kinesis.KinesisUtils

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Time, StreamingContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.storage.StorageLevel

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.kinesis.AmazonKinesisClient
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream

object RDDAnalysis {
  def main(args: Array[String]){

    if (args.length < 3) {
      System.err.println("Usage: KinesisWordCount <host> <app_name> <stream_name> <kinesis_endpoint>")
      System.exit(1)
    }

    val Array(host, appName, streamName, endpointUrl, regionName) = args

    val kinesisClient = new AmazonKinesisClient(new DefaultAWSCredentialsProviderChain())
    kinesisClient.setEndpoint(endpointUrl)
    val numShards = kinesisClient.describeStream(streamName).getStreamDescription().getShards()
      .size()

    val numStreams = numShards

    val batchInterval = Milliseconds(2000)
    val sparkConfig = new SparkConf().setAppName("ssc-test").setMaster(host)
    val sc = new SparkContext(sparkConfig)
    val ssc = new StreamingContext(sc, batchInterval)

    val kinesisCheckpointInterval = batchInterval

    val kinesisStreams = (0 until numStreams).map { i =>
      KinesisUtils.createStream(ssc, appName, streamName, endpointUrl, regionName,
        InitialPositionInStream.LATEST, kinesisCheckpointInterval,StorageLevel.MEMORY_ONLY)
    }

    val unionStreams = ssc.union(kinesisStreams)
    val words = unionStreams.flatMap(byteArray => new String(byteArray).split(" "))

    words.foreachRDD(foreachFunc = (rdd: RDD[String], time: Time) => {
      //println(rdd.count())
      println(rdd)
    })

    ssc.start()
    ssc.awaitTermination()
  }
}

/** Case class for converting RDD to DataFrame */
case class Record(id: String, time: String, level: String, method: String, uri: String, reqtime: String, foobar: String)

/** Lazily instantiated singleton instance of SQLContext */
object SQLContextSingleton {

  @transient  private var instance: SQLContext = _

  def getInstance(sparkContext: SparkContext): SQLContext = {
    if (instance == null) {
      instance = new SQLContext(sparkContext)
    }
    instance
  }
}