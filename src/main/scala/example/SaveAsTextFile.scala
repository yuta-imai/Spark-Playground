package spark.example

import org.apache.spark._

object SaveAsTextFile {
  def main(args: Array[String]): Unit ={
    val Array(host) = args
    val conf = new SparkConf().setAppName("SaveAsTextFile").setMaster(host)
    val sc = new SparkContext(conf)

    val rdd = sc.parallelize(Array(1,2,3,4,5,6))
    rdd.saveAsTextFile("file:///home/yuimai/")
  }
}
