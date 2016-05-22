package com.imaifactory.sparkplayground

/**
  * Created by yimai on 11/16/15.
  */

import java.util.NoSuchElementException

import org.apache.spark._
import org.apache.log4j.LogManager
import org.apache.log4j.Level

object WordCount {
  LogManager.getRootLogger.setLevel(Level.ALL)

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    conf.setAppName(this.getClass.toString)
    try {
      conf.get("spark.master")
    } catch {
      case e: NoSuchElementException => conf.setMaster("local[*]")
    }
    val sc = new SparkContext(conf)

    val text = "Hello Spark, this is my first Spark application."
    val textArray = text.split(" ").map(_.replaceAll(" ",""))
    val firstRdd  = sc.parallelize(textArray)
    val secondRdd = firstRdd.map(item => (item, 1))
    val finalRdd  = secondRdd.reduceByKey((x,y) => x + y)
    val result = finalRdd.collect()
    result.foreach(i => println(i))
  }
}
