import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "ResourceManagementTool"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "org.scalatest" % "scalatest_2.9.0" % "1.8"
      //"com.twitter" % "querulous" % "2.6.5" ,
      //"net.liftweb" %% "lift-json" % "2.4" //,
      //"com.sun.jersey" % "jersey-server" % "1.4" ,
     // "com.sun.jersey" % "jersey-core" % "1.4" //,
     //"postgresql" % "postgresql" % "9.1-901.jdbc4"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here
      //resolvers += "org.scalatest" %% "scalatest" % "1.8" % "test" ,
      resolvers += "Twitter repo" at "http://maven.twttr.com/" ,
      resolvers += "DevJava repo" at "http://download.java.net/maven/2/"
    )

}
