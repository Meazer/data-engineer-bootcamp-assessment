name := "linkit"

version := "0.1"

scalaVersion := "2.11.12"

resolvers += "Hortonworks Repository" at "http://repo.hortonworks.com/content/repositories/releases/"

libraryDependencies +=	"org.apache.hbase" % "hbase-client" % "1.1.8"
libraryDependencies += "org.apache.hbase" % "hbase-common" % "1.1.8"
libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "2.7.0"
libraryDependencies += "com.typesafe" % "config" % "1.3.2"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.0"
libraryDependencies += "com.hortonworks" % "shc-core" % "1.1.1-2.1-s_2.11"