package com.imaifactory.sparkplayground

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql._

object SparkRedshift {
  def main (args: Array[String]) {

    val Array(host) = args

    val sparkConfig = new SparkConf().setAppName("spark-redshift").setMaster(host)
    val sc = new SparkContext(sparkConfig)
    val sqlContext = new SQLContext(sc)

    // Get some data from a Redshift table
    val df: DataFrame = sqlContext.read
      .format("com.databricks.spark.redshift")
      .option("url", "jdbc:postgresql://YOURREDSHIFT:5439/mydb?user=USER&password=PASSWORD")
      .option("dbtable", "uservisits")
      .option("tempdir", "s3n://BUCKET/PREFIX")
      .load()
    df.select("sourceip").show()
  }
}
