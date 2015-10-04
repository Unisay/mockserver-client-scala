name := "mockserver-client-scala"

version := "0.1.0"

scalaVersion := "2.11.7"

val mockserverVersion = "3.10.0"

libraryDependencies ++= Seq(
  "org.mock-server" % "mockserver-client-java" % mockserverVersion,
  // Logging
  "org.slf4j" % "slf4j-api" % "1.7.12",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.0.0",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  // Test
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test",
  "org.scalatest" %% "scalatest" % "2.2.5" % "test",
  "org.mock-server" % "mockserver-netty" % mockserverVersion % "test"
)


lazy val `mockserver-client-scala` = project in file(".")