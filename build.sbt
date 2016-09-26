name := "crawler"

version := "1.0"

scalaVersion := "2.11.8"

lazy val root =
  project.in(file("."))
    .configs( IntegrationTest )
    .settings( Defaults.itSettings : _*)

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0"
libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.3.0"
libraryDependencies += "org.jsoup" % "jsoup" % "1.8.3"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "it,test"

mainClass in assembly := Some("org.fingertap.crawler.CrawlerApp")