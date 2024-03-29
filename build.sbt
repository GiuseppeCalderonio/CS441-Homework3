name := "CS441-Homework3"

version := "0.1.0-SNAPSHOT"

scalaVersion := "3.0.2"

val logbackVersion = "1.3.0-alpha10"
val sfl4sVersion = "2.0.0-alpha5"
val typesafeConfigVersion = "1.4.1"
val apacheCommonIOVersion = "2.11.0"
val scalacticVersion = "3.2.9"
val cloudSimPlusVersion = "7.3.0"

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-core" % logbackVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "org.slf4j" % "slf4j-api" % sfl4sVersion,
  "commons-io" % "commons-io" % apacheCommonIOVersion,
  "org.scalactic" %% "scalactic" % scalacticVersion,
  "org.scalatest" %% "scalatest" % scalacticVersion % Test,
  "org.scalatest" %% "scalatest-featurespec" % scalacticVersion % Test,
  "org.cloudsimplus" % "cloudsim-plus" % cloudSimPlusVersion,
  "com.typesafe" % "config" % typesafeConfigVersion

)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) =>
    xs map {
      _.toLowerCase
    } match {
      case "services" :: xs =>
        MergeStrategy.filterDistinctLines
      case _ => MergeStrategy.discard
    }
  case x => MergeStrategy.first
}

