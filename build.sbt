name := "HelloSpark-Kinesis"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % "1.4.1",
  "org.apache.spark" % "spark-streaming_2.11" % "1.4.1",
  "org.apache.spark" % "spark-streaming-kinesis-asl_2.11" % "1.4.1",
  "org.apache.spark" % "spark-sql_2.11" % "1.4.1",
  "com.amazonaws" % "amazon-kinesis-client" % "1.5.1",
  "com.amazonaws" % "aws-java-sdk" % "1.10.6"
)

dependencyOverrides ++= Set(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4"
)