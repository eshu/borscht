ThisBuild / organization := "h8.io"
ThisBuild / scalaVersion := "3.0.0-M1"

ThisBuild / libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.3" % Test

lazy val core = project
  .in(file("core"))
  .settings(name := "borscht-core")

lazy val typesafe = project
  .in(file("cfg/typesafe"))
  .settings(
    name := "borscht-cfg-typesafe",

    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.4.0",
      "org.antlr" % "ST4" % "4.3.1"))
  .dependsOn(core)

lazy val tests = project
  .in(file("tests"))
  .settings(
    name := "borscht-tests",
    publishArtifact := false)
  .dependsOn(core, typesafe)

lazy val root = project
  .in(file("."))
  .settings(
    name := "borscht",
    publishArtifact := false)
  .aggregate(core, typesafe, tests)
