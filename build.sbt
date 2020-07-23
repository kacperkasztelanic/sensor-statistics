name := "sensor-statistics"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.11"

scalacOptions ++= Seq(
  "-encoding", "UTF-8",   // source files are in UTF-8
  "-deprecation",         // warn about use of deprecated APIs
  "-unchecked",           // warn about unchecked type parameters
  "-feature",             // warn about misused language features
  "-Xlint",               // enable handy linter warnings
  "-Xfatal-warnings"      // turn compiler warnings into errors
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.0" % Test withSources() withJavadoc()
)
