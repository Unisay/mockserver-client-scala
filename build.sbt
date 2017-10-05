lazy val `mockserver-client-scala` = (project in file("."))
  .settings(name := "mockserver-client-scala")
  .settings(version := "0.3.0")
  .settings(scalaVersion := "2.12.3")
  .settings(organization := "com.github.unisay")
  .settings(licenses += ("MIT", url("http://opensource.org/licenses/MIT")))
  .settings(publishMavenStyle := true)
  .settings(bintrayPackageLabels := Seq("mockserver", "scala", "rest", "testing"))
  .settings(bintrayRepository := "maven")
  .settings(bintrayOrganization := None)
  .settings(libraryDependencies ++= Seq(
    "org.mock-server"            %  "mockserver-client-java"      % "3.11",
    "org.slf4j"                  %  "slf4j-api"                   % "1.7.25",
    "ch.qos.logback"             %  "logback-classic"             % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging"               % "3.7.2",
    "org.scalatest"              %% "scalatest"                   % "3.0.1"    % "test",
    "org.scalamock"              %% "scalamock-scalatest-support" % "3.6.0"    % "test"
  ))
  .settings(scalacOptions ++= Seq(
    "-encoding", "UTF-8",
    "-deprecation", // warning and location for usages of deprecated APIs
    "-feature", // warning and location for usages of features that should be imported explicitly
    "-unchecked", // additional warnings where generated code depends on assumptions
    "-Xlint:_", // recommended additional warnings
    "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
    "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
    "-Ywarn-unused-import", // Warn when imports are unused
    "-Ywarn-unused", // Warn when local and private vals, vars, defs, and types are unused
    "-Ywarn-numeric-widen", // Warn when numerics are widened, Int and Double, for instance
    "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
    "-Ywarn-dead-code", // Warn when dead code is identified
    "-Ywarn-infer-any", // Warn when a type argument is inferred to be `Any`
    "-Ywarn-nullary-override", //  Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Ywarn-nullary-unit", // Warn when nullary methods return Unit
    "-language:reflectiveCalls",
    "-language:postfixOps" // too lazy?
  ))
  .settings(crossScalaVersions ++= Seq("2.11.11", "2.12.3"))
