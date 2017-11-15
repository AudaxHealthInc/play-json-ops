
lazy val commonRootSettings = Seq(
  scalaVersion := "2.11.8",
  organization := "com.rallyhealth",
  organizationName := "Rally Health"
)

lazy val root = Project("play-json-ops-root", file("."))
  .aggregate(`play-json-ops-23`, `play-json-tests-23`, `play-json-ops-25`, `play-json-tests-25`)
  .settings(commonRootSettings ++ Seq(
    // don't publish the surrounding multi-project build
    publish := {},
    publishLocal := {}
  ))

val PlayJsonVersion = new {
  val _23 = "2.3.10"
  val _25 = "2.5.10"
}

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

def playJsonOpsCommon(playVersion: String) = common ++ Seq(
  libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-json" % playVersion
  )
)

lazy val `play-json-ops-23` = project in file("play-json-ops-23") settings (
  playJsonOpsCommon(PlayJsonVersion._23),
  name := "play-json-ops",
  crossScalaVersions := Seq("2.11.8", "2.10.6")
)

lazy val `play-json-ops-25` = project in file("play-json-ops-25") settings (
  playJsonOpsCommon(PlayJsonVersion._25),
  name := "play-json-ops-25"
)

def playJsonTestsCommon(playVersion: String) = common ++ Seq(
  libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-json" % playVersion
  )
)

lazy val `play-json-tests-23` = project in file("play-json-tests-23") settings (
  playJsonTestsCommon(PlayJsonVersion._23),
  name := "play-json-tests",
  crossScalaVersions := Seq("2.11.8", "2.10.6"),
  libraryDependencies ++= Seq(
    "org.scalacheck" %% "scalacheck" % "1.12.5",
    "org.scalatest" %% "scalatest" % "2.2.6",
    "com.rallyhealth" %% "scalacheck-ops" % "1.5.0"
  )
) dependsOn `play-json-ops-23`

lazy val `play-json-tests-25` = project in file("play-json-tests-25") settings(common: _*) settings (
  playJsonTestsCommon(PlayJsonVersion._25),
  name := "play-json-tests-25",
  libraryDependencies ++= Seq(
    "org.scalacheck" %% "scalacheck" % "1.13.4",
    "org.scalatest" %% "scalatest" % "3.0.4",
    "com.rallyhealth" %% "scalacheck-ops_1-13" % "1.5.0"
  )
) dependsOn `play-json-ops-25`
