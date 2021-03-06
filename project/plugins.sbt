// See https://wiki.audaxhealth.com/display/ENG/Build+Structure#BuildStructure-Localconfiguration
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

resolvers += Resolver.url("Rally Plugin Releases", url("https://artifacts.werally.in/artifactory/ivy-plugins-release"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.rallyhealth" %% "rally-versioning" % "0.3.1")
addSbtPlugin("com.rallyhealth" %% "rally-sbt-plugin" % "0.4.0")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")

