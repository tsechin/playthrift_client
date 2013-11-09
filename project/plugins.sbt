// for sbt 0.13
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.3.0")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.2")

// scrooge-sbt-plugin not availabe for sbt 0.13 (as of 20130822)
// hence the inclusion of ScroogeSBT.scala
//addSbtPlugin("com.twitter" %% "scrooge-sbt-plugin" % "3.6.0")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.9.1")

