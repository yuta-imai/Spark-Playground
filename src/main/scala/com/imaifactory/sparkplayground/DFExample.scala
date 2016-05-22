package com.imaifactory.sparkplayground

/**
  * Created by yimai on 2016/05/20.
  */

import org.apache.spark._
import org.apache.spark.sql._

object DFExample {

  var inpath = "/Users/yimai/Playground/Spark-Playground/src/main/resouces/sample_json/"

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName(this.getClass.toString)
    conf.setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._
    val df = sqlContext.read.json(inpath)

    case class ClickLog(adId: String,
                       hostname: String,
                       impressionId: String,
                       number: String,
                       requestBeginTime: String,
                       requestEndTime: String,
                       threadId: String,
                       dt: String)

    df.printSchema()
    val ds = df.as("ClickLog")
    ds.groupBy("hostname").count().show()

    Seq(1,2,3,4,5).toDS().show

  }
}
