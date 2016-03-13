import sbt._

object Dependencies {
  val akkaVersion = "2.4.1"
  val sprayVersion = "1.3.3"
  val sprayJsonVersion = "1.3.2"
  val slickVersion = "3.1.1"
  val slf4jVersion = "1.6.4"
  val h2Version = "1.3.170"

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val sprayCan = "io.spray" %% "spray-can" % sprayVersion
  val sprayRouting = "io.spray" %% "spray-routing" % sprayVersion
  val sprayJson = "io.spray" %% "spray-json" % sprayJsonVersion
  val slick = "com.typesafe.slick" %% "slick" % slickVersion
  val slickCodegen = "com.typesafe.slick" %% "slick-codegen" % slickVersion % "compile"
  val slf4j = "org.slf4j" % "slf4j-nop" % slf4jVersion
  val h2 = "com.h2database" % "h2" % h2Version

  val dependencies = Seq(
    akkaActor,
    sprayCan,
    sprayRouting,
    sprayJson,
    slick,
    slickCodegen,
    slf4j,
    h2
  )
}
