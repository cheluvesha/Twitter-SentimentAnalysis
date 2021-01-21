name := "SentimentAnalysis"

version := "0.1"

scalaVersion := "2.12.10"

coverageEnabled := true

scapegoatVersion in ThisBuild := "1.3.8"

libraryDependencies += "org.apache.spark" %% "spark-core" % "3.0.0" % "provided"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.0.0" % "provided"

libraryDependencies += "org.apache.spark" %% "spark-mllib" % "3.0.0" % "provided"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "3.0.0" % "provided"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.4.1"

libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.0"

libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.0.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % Test

assemblyMergeStrategy in assembly := {
  case PathList("org", "aopalliance", xs @ _*)      => MergeStrategy.last
  case PathList("javax", "inject", xs @ _*)         => MergeStrategy.last
  case PathList("javax", "servlet", xs @ _*)        => MergeStrategy.last
  case PathList("javax", "activation", xs @ _*)     => MergeStrategy.last
  case PathList("org", "apache", xs @ _*)           => MergeStrategy.last
  case PathList("com", "google", xs @ _*)           => MergeStrategy.last
  case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
  case PathList("com", "codahale", xs @ _*)         => MergeStrategy.last
  case PathList("com", "yammer", xs @ _*)           => MergeStrategy.last
  case "about.html"                                 => MergeStrategy.rename
  case "META-INF/ECLIPSEF.RSA"                      => MergeStrategy.last
  case "META-INF/mailcap"                           => MergeStrategy.last
  case "META-INF/mimetypes.default"                 => MergeStrategy.last
  case "plugin.properties"                          => MergeStrategy.last
  case "log4j.properties"                           => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
