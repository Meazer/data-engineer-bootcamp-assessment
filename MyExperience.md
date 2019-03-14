#My experience and further notes

Due to some restrictions I couldn't run HDP so I did the alternative steps.

## 1st Phase) Create a Scala application

### Spark

- I started reading some docs about Scala and how to create a scala application.
- First I tried to use IntelliJ IDEA with Scala plugin but it was too slow.
- I installed and configured JDK and sbt. then test some Scala code using sbt.
- Then I installed Spark and run a "Hello World" app. here I used Sublime Text3 editor.
- After watching some Spark's tutorials I was able to read the csv files and do the requirements in spark section.  

### HBase

I spent most of my assessment time trying here!:

- Since I use Windows I installed HBase using Cygwin: https://hbase.apache.org/cygwin.html.
- After that I tried to make the connection between Spark and HBase but it throwed `java.lang.NoClassDefFoundError: org/apache/hadoop/hbase/HBaseConfiguration` I couldn't solve it until I rebuild the app using IntelliJ IDEA this time!
- Then I tried to load csv to HBase using HBase shell : `./bin/hbase org.apache.hadoop.hbase.mapreduce.ImportTsv -Dimporttsv.separator=',' -Dimporttsv.columns='HBASE_ROW_KEY, main:eventId, main:driverId, main:driverName, main:eventTime, main:eventType, main:latitudeColumn, main:longitudeColumn, main:routeId, main:routeName, main:truckId' dangerous_driving ./tmp/dangerous-driver.csv` but it didn't work.
-I tried to load cvs file from Spark use Spark HBase connector : https://github.com/hortonworks-spark/shc but got `json4s error`.
- Finally I used `table.put` to load the csv file line by line by when read the file using `spark.read` I got `Error: Task not serializable` so I used `Source.fromFile` to read the file and load it to HBase.


## 2nd Phase) Kafka Ingestion and Streaming

-First I installed Docker toolbox on windows and run "hello-world" image.
-Then I watched a kafka tutorial.
-I installed Kafka and run a Kafka-connect to produce a test file and consume it: `connect-standalone.sh config/connect-standalone.properties config/connect-file-source.properties config/connect-file-sink.properties`

Here I also tried to run several Kafka images like `https://hub.docker.com/r/landoop/fast-data-dev` and `https://hub.docker.com/r/wurstmeister/kafka` but it didn't work for several reasons.
