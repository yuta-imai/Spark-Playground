name := "Spark-Playground"

version := "1.0"

scalaVersion := "2.10.6"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "org.apache.spark" % "spark-core_2.10" % "1.6.1",
  "org.apache.spark" % "spark-streaming_2.10" % "1.6.1",
  "org.apache.spark" % "spark-streaming-twitter_2.10" % "1.6.1",
  "org.apache.spark" % "spark-streaming-kinesis-asl_2.10" % "1.6.1",
  "org.apache.spark" % "spark-sql_2.10" % "1.6.1",
  "org.apache.spark" % "spark-yarn_2.10" % "1.6.1",
  "com.amazonaws" % "amazon-kinesis-client" % "1.6.3",
  "com.amazonaws" % "aws-java-sdk" % "1.10.6",
  "com.typesafe" % "config" % "1.3.0"
)

dependencyOverrides ++= Set(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4"
)

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".properties" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".xml" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".types" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".class" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".so" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".dll" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".jnilib" => MergeStrategy.first
  case "application.conf"                            => MergeStrategy.concat
  case "unwanted.txt"                                => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
