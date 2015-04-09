
name := "play-json-ops"

organization := "me.jeffmay"

version := "0.2.1"

crossScalaVersions := Seq("2.10.4", "2.11.6")

scalacOptions := {
  // the deprecation:false flag is only supported by scala >= 2.11.3, but needed for scala >= 2.11.0 to avoid warnings
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, scalaMinor)) if scalaMinor >= 11 =>
      // For scala versions >= 2.11.3
      Seq("-Xfatal-warnings", "-deprecation:false")
    case Some((2, scalaMinor)) if scalaMinor < 11 =>
      // For scala versions 2.10.x
      Seq("-Xfatal-warnings")
  }
}

libraryDependencies := Seq(
  "com.typesafe.play" %% "play-json" % "2.3.7",
  "org.scalacheck" %% "scalacheck" % "1.12.2" % "test",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
).map(_.withSources())

publishTo <<= version { version: String =>
  val repoBaseUrl = "https://artifacts.werally.in/artifactory/"
  val (name, url) = if (version.contains("-SNAPSHOT"))
    ("libs-snapshot-local", repoBaseUrl + "libs-snapshot-local")
  else
    ("libs-release-local", repoBaseUrl + "libs-release-local")
  Some(Resolver.url(name, new URL(url))(Resolver.mavenStylePatterns))
}

// All of the published versions
resolvers += "Artifactory Libs Release" at "https://artifacts.werally.in/artifactory/libs-release"

sources in(Compile, doc) := Seq.empty

// disable publishing the jar produced by `test:package`
publishArtifact in(Test, packageBin) := false

// disable publishing the test sources jar
publishArtifact in(Test, packageSrc) := false
