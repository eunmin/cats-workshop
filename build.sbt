name := "cats-workshop"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot-starter-web" % "2.3.1.RELEASE",
  "org.springframework.boot" % "spring-boot-starter-data-mongodb" % "2.3.1.RELEASE",
  "javax.persistence" % "persistence-api" % "1.0.2",
  "io.springfox" % "springfox-swagger2" % "2.9.2",
  "io.springfox" % "springfox-swagger-ui" % "2.9.2",
  "org.typelevel" %% "cats-core" % "2.0.0",
  "org.typelevel" %% "cats-effect" % "2.1.3",
  "org.typelevel" %% "cats-free" % "2.1.0"
)

mainClass in Compile := Some("com.eunmin.webapp.Main")
