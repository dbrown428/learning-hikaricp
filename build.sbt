ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.danicabrown"

lazy val root = (project in file("."))
    .settings(
        name := "cpool",
        libraryDependencies  ++= Seq(
            "org.scalatest" %% "scalatest" % "3.0.5" % "test",
            "org.postgresql" % "postgresql" % "42.2.2",
            "com.zaxxer" % "HikariCP" % "2.3.2",
        )
    )
