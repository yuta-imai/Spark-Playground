name := "HelloSpark-Kinesis"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % "1.3.1",
  "org.apache.spark" % "spark-streaming_2.11" % "1.3.1",
  "org.apache.spark" % "spark-streaming-kinesis-asl_2.11" % "1.3.1",
  "org.apache.spark" % "spark-sql_2.11" % "1.3.1",
  "com.amazonaws" % "amazon-kinesis-client" % "1.3.0",
  "com.amazonaws" % "aws-java-sdk" % "1.9.34"
)
