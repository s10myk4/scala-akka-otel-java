scalaVersion := "2.13.10"
name := "scala-akka-otel-java"
organization := "s10myk4"

enablePlugins(JavaAgent)
libraryDependencies ++= {
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.12",
    "io.opentelemetry" % "opentelemetry-bom" % "1.28.0" pomOnly(),
    "io.opentelemetry" % "opentelemetry-api" % "1.28.0",
    "io.opentelemetry" % "opentelemetry-sdk" % "1.28.0",
    "io.opentelemetry" % "opentelemetry-exporter-otlp" % "1.28.0",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.8.3",
  )
}
javaAgents += JavaAgent("io.opentelemetry.javaagent" % "opentelemetry-javaagent" % "1.28.0" % "runtime")
javaOptions += "-Dotel.javaagent.configuration-file=src/main/resources/otel.properties"
//javaOptions += "-Dotel.javaagent.debug=true",
