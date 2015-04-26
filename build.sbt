name := """tomorrow"""

version := "1.0"

scalaVersion := "2.11.5"

resolvers += Resolver.url("me.mtrupkin ivy repo", url("http://dl.bintray.com/mtrupkin/ivy/"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.9",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.9" % "test",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "com.typesafe.play" %% "play-json" % "2.4.0-M2",
  "org.scalafx" %% "scalafx" % "8.0.20-R6",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.2.2",
  "me.mtrupkin.console" %% "console-core" % "0.8-SNAPSHOT"
)
