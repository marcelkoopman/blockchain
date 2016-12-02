name := "blockchain"

version := "1.0"

scalaVersion := "2.12.0"

val akkaVersion = "2.4.14"

resolvers += "api-v1-client-java-mvn-repo" at "https://raw.githubusercontent.com/blockchain/api-v1-client-java/mvn-repo/"

libraryDependencies ++= Seq(
  "info.blockchain" % "api" % "1.1.4",
  "com.typesafe.akka" %% "akka-actor" % "2.4.14"
)