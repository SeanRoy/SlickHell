import sbt._
import Keys._
import org.scalatra.sbt._
import skinny.scalate.ScalatePlugin._
import ScalateKeys._

object SlickHellBuild extends Build {
  val Organization = "com.ping4"
  val Name = "SlickHell"
  val Version = "0.1.0"
  val ScalaVersion = "2.11.2"
  val ScalatraVersion = "2.6.+"
  val AWSVersion = "1.11.128"
  val SlickPG = "0.15.4"

  lazy val project = Project (
    "SlickHell",
    file("."),
    settings = ScalatraPlugin.scalatraWithJRebel ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "Typesafe Maven Repository" at "http://repo.typesafe.com/typesafe/maven-releases/",
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "org.scalatra" %% "scalatra-json" % ScalatraVersion,
        "ch.qos.logback" % "logback-classic" % "1.1.3" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "9.1.5.v20140505" % "container",
        "org.eclipse.jetty" % "jetty-plus" % "9.1.5.v20140505" % "container",
        "org.eclipse.jetty" % "jetty-servlets" % "9.1.5.v20140505",
        "javax.servlet" % "javax.servlet-api" % "3.1.0",
        "org.json4s" %% "json4s-jackson" % "3.6.0-M2",
        "redis.clients" % "jedis" % "2.5.1",
        "com.typesafe" % "config" % "1.2.1",
        "com.typesafe.slick" %% "slick" % "3.0.0",
        "com.typesafe.slick" %% "slick-extensions" % "3.0.0",
        "com.mchange" % "c3p0" % "0.9.5.1",
        "org.postgresql" % "postgresql" % "9.3-1102-jdbc41",
        "commons-codec" % "commons-codec" % "1.9",
        "io.spray" %% "spray-json" % "1.3.4",
        "com.github.tminglei" %% "slick-pg" % SlickPG,
        "com.github.tminglei" %% "slick-pg_core" % SlickPG,
        "com.github.tminglei" %% "slick-pg_joda-time" % SlickPG,
        "com.github.tminglei" %% "slick-pg_jts" % SlickPG,
        "com.vividsolutions" % "jts" % "1.13",
        "me.moocar" % "logback-gelf" % "0.11",
        "com.outworkers" %% "phantom-dsl" % "2.16.4",
        "com.amazonaws" % "aws-java-sdk-s3" % AWSVersion,
        "com.amazonaws" % "aws-java-sdk-sns" % AWSVersion,
        "com.amazonaws" % "aws-java-sdk-cloudsearch" % AWSVersion,
        "com.amazonaws" % "aws-java-sdk-lambda" % AWSVersion,
        "com.amazonaws" % "aws-java-sdk-dynamodb" % AWSVersion,
        "org.apache.commons" % "commons-email" % "1.4",
        "org.hashids" % "hashids" % "1.0.1",
        "org.atteo" % "evo-inflector" % "1.2.2",
        "org.jsoup" % "jsoup" % "1.11.2",
        "org.scalatest" %% "scalatest" % "3.0.4" % "test",
        "org.scalactic" %% "scalactic" % "3.0.4"
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  )
}
