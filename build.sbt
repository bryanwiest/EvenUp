lazy val root = project
  .in(file("."))
  .settings(
    name := "EvenUp",
    version := "1.0.0",

    scalaVersion := "3.7.4",

    scalacOptions += "-Wconf:msg=Implicit parameters should be provided with a `using` clause:s",
    coverageExcludedPackages := "de\\.htwg\\.swe\\.evenup\\.EvenUp.*;de\\.htwg\\.swe\\.evenup\\.view\\.gui\\..*;de\\.htwg\\.swe\\.evenup\\.view\\.tui\\.Tui.*",

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.2.1" % Test,
      "org.scalactic" %% "scalactic" % "3.2.19", 
      "org.scalatest" %% "scalatest" % "3.2.19" % "test",
      "org.scalafx" %% "scalafx" % "24.0.2-R36",
      "org.scala-lang.modules" %% "scala-xml" % "2.4.0",
      "org.playframework" %% "play-json" % "3.0.6"
    ),
    
    assembly / assemblyJarName := "EvenUp.jar",
    assembly / mainClass := Some("de.htwg.swe.evenup.EvenUp"),
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", _*) => MergeStrategy.discard
      case _                        => MergeStrategy.first
    }
  )