name := "SocialNetworks"

version := "0.1"

scalaVersion := "2.11.8"

val sparkVersion = "2.2.1"

resolvers += "SparkPackages" at "https://dl.bintray.com/spark-packages/maven"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided"
)
// https://mvnrepository.com/artifact/graphframes/graphframes
libraryDependencies += "graphframes" % "graphframes" % "0.6.0-spark2.2-s_2.11" % "provided"



