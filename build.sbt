name := "sensor-statistics"
version := "1.0.0"

scalaVersion := "2.13.3"

scalacOptions ++= Seq(
  "-encoding", "UTF-8",   // source files are in UTF-8
  "-deprecation",         // warn about use of deprecated APIs
  "-unchecked",           // warn about unchecked type parameters
  "-feature",             // warn about misused language features
  "-Xlint",               // enable handy linter warnings
  "-Xfatal-warnings"      // turn compiler warnings into errors
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.0" % Test withSources() withJavadoc(),
  "org.scalatestplus" %% "scalacheck-1-14" % "3.2.0.0" % Test withSources() withJavadoc()
)
