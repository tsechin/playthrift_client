// Project

name := "playthrift_client"

version := "1.0"

scalaVersion := "2.10.2"

// do not include scala-library.jar in .war
autoScalaLibrary := false

crossPaths := false

libraryDependencies +=  "org.apache.thrift" % "libthrift" % "0.9.1"

//com.twitter.scrooge.ScroogeSBT.newSettings

scalacOptions ++= Seq("-feature", "-deprecation", "-language:higherKinds")

// tell sbteclipse to generate a java project
EclipseKeys.projectFlavor := EclipseProjectFlavor.Java

// deps for this project

libraryDependencies ++= Seq(
	"org.bouncycastle" % "bcprov-jdk15on" % "1.49",
	"org.bouncycastle" % "bcprov-ext-jdk15on" % "1.49",
	"org.bouncycastle" % "bcpkix-jdk15on" % "1.49",
	"org.bouncycastle" % "bcmail-jdk15on" % "1.49",
	"org.slf4j" % "slf4j-api" % "1.7.5",
	"org.slf4j" % "slf4j-simple" % "1.7.5",
	"com.google.guava" % "guava" % "15.0",
	"commons-pool" % "commons-pool" % "1.6"
)


// sbt-idea: exclude IDEA folders

ideaExcludeFolders += ".idea"

ideaExcludeFolders += ".idea_modules"

mainClass in (Compile, run) := Some("playthrift_client.Main")
