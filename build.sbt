
lazy val common = Seq(

  organization := "com.rallyhealth",

  organizationName := "Rally Health",

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

  libraryDependencies := Seq(
    "com.typesafe.play" %% "play-json" % "2.3.7"
  )

) dependsOn (
  playJsonTests % "compile->test"
)

lazy val playJsonTests = project in file("playJsonTests") settings(common: _*) settings (

  name := "play-json-tests",

  libraryDependencies := Seq(
    "com.typesafe.play" %% "play-json" % "2.3.7",
    "org.scalacheck" %% "scalacheck" % "1.12.2",
    "org.scalatest" %% "scalatest" % "2.2.4",
    "com.rallyhealth" %% "scalacheck-ops" % "1.0.0"
  )
)

// don't publish the surrounding multi-project build
publish := {}
