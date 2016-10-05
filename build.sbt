
lazy val commonRootSettings = Seq(
  scalaVersion := "2.11.8",
  organization := "com.rallyhealth",
  organizationName := "Rally Health"
)

lazy val root = Project("play-json-ops-root", file("."), Seq(playJsonOps, playJsonTests))
  .settings(commonRootSettings ++ Seq(
    // don't publish the surrounding multi-project build
    publish := {},
    publishLocal := {}
  ))

val playJsonVersion = "2.3.10"

lazy val common = commonRootSettings ++ Seq(

  // add options not present in rally-sbt-plugin
  scalacOptions ++= Seq(
    "-Ywarn-dead-code",
    "-encoding", "UTF-8"
  ),

  ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) },

  // don't publish the test code as an artifact anymore, since we have playJsonTests
  publishArtifact in Test := false,

  // disable compilation of ScalaDocs, since this always breaks on links
  sources in(Compile, doc) := Seq.empty,

  // disable publishing empty ScalaDocs
  publishArtifact in (Compile, packageDoc) := false,

  licenses += ("Apache-2.0", url("http://opensource.org/licenses/apache-2.0"))
)

lazy val playJsonOps = project in file("playJsonOps") settings(common: _*) settings (

  name := "play-json-ops",

  libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-json" % playJsonVersion
  )

) dependsOn (
  playJsonTests % "test"
)

lazy val playJsonTests = project in file("playJsonTests") settings(common: _*) settings (

  name := "play-json-tests",

  libraryDependencies ++= Seq(
    "com.rallyhealth" %% "scalacheck-ops" % "1.3.0",
    "com.typesafe.play" %% "play-json" % playJsonVersion,
    "org.scalacheck" %% "scalacheck" % "1.12.5",
    "org.scalatest" %% "scalatest" % "2.2.6"
  )
)
