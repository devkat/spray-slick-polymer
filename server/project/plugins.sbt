resolvers ++= Seq(
  "sonatype-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.2")

addSbtPlugin("com.github.sbtliquibase" % "sbt-liquibase" % "0.1.0-SNAPSHOT")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")