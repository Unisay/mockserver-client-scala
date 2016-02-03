lazy val `mockserver-client-scala` = (project in file("."))
  .settings(name := "mockserver-client-scala")
  .settings(version := "0.2.0")
  .settings(scalaVersion := "2.11.7")
  .settings(organization := "com.github.unisay")
  .settings(licenses += ("MIT", url("http://opensource.org/licenses/MIT")))
  .settings(publishMavenStyle := true)
  .settings(bintrayPackageLabels := Seq("mockserver", "scala", "rest", "testing"))
  .settings(bintrayRepository := "maven")
  .settings(bintrayOrganization := None)
  .settings(libraryDependencies ++= Seq(
    "org.mock-server"            %  "mockserver-client-java"      % "3.10.0",
    "org.slf4j"                  %  "slf4j-api"                   % "1.7.12",
    "ch.qos.logback"             %  "logback-classic"             % "1.1.3",
    "com.typesafe.scala-logging" %% "scala-logging"               % "3.0.0",
    "org.scalatest"              %% "scalatest"                   % "2.2.5"    % "test",
    "org.scalamock"              %% "scalamock-scalatest-support" % "3.2.2"    % "test"
  ))