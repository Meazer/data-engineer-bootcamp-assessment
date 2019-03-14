import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.util.Bytes

import com.typesafe.config.{Config, ConfigFactory}

import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

import org.apache.spark.sql.execution.datasources.hbase._
import scala.io.Source


object Hbase {

//  def catalog = s"""{
//                   |"table":{"namespace":"default", "name":"dangerous"},
//                   |"rowkey":"key",
//                   |"columns":{
//                    |"eventId":{"cf":"rowkey", "col":"key", "type":"string"},
//                    |"driverId":{"cf":"data", "col":"driverId", "type":"string"},
//                    |"driverName":{"cf":"data", "col":"driverName", "type":"string"},
//                    |"eventTime":{"cf":"data", "col":"eventTime", "type":"string"},
//                    |"eventType":{"cf":"data", "col":"eventType", "type":"string"},
//                    |"latitudeColumn":{"cf":"data", "col":"latitudeColumn", "type":"string"},
//                    |"longitudeColumn":{"cf":"data", "col":"longitudeColumn", "type":"string"},
//                    |"routeId":{"cf":"data", "col":"routeId", "type":"string"},
//                    |"routeName":{"cf":"data", "col":"routeName", "type":"string"},
//                    |"truckId":{"cf":"data", "col":"truckId", "type":"string"}
//                    |}
//                   |}""".stripMargin
//Error: json4s error

  def buildPut(record: String) ={
    val attr = record.split(",")
    val key = Bytes.toBytes(attr(0))
    val row = new Put(key)
    row.addColumn( Bytes.toBytes("data"), Bytes.toBytes("driverId"),Bytes.toBytes(attr(1)))
    row.addColumn( Bytes.toBytes("data"), Bytes.toBytes("driverName"),Bytes.toBytes(attr(2)))
    row.addColumn( Bytes.toBytes("data"), Bytes.toBytes("eventTime"),Bytes.toBytes(attr(3)))
    row.addColumn( Bytes.toBytes("data"), Bytes.toBytes("eventType"),Bytes.toBytes(attr(4)))
    row.addColumn( Bytes.toBytes("data"), Bytes.toBytes("latitudeColumn"),Bytes.toBytes(attr(5)))
    row.addColumn( Bytes.toBytes("data"), Bytes.toBytes("longitudeColumn"),Bytes.toBytes(attr(6)))
    row.addColumn( Bytes.toBytes("data"), Bytes.toBytes("routeId"),Bytes.toBytes(attr(7)))
    row.addColumn( Bytes.toBytes("data"), Bytes.toBytes("routeName"),Bytes.toBytes(attr(8)))
    row.addColumn( Bytes.toBytes("data"), Bytes.toBytes("truckId"),Bytes.toBytes(attr(9)))
    row
  }

  def main(args: Array[String])  {


    val spark = SparkSession.builder
      .master("local")
      .appName("Drivers")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    val hbaseConfig = HBaseConfiguration.create()
    hbaseConfig.set( "hbase.zookeeper.quorum","localhost")
    hbaseConfig.set( "hbase.zookeeper.property.clientPort","2181")
    val connection = ConnectionFactory.createConnection(hbaseConfig)
    val table = connection.getTable(TableName.valueOf("dangerous_driving"))

// ------- when try to use read the csv file using spark.read then use foreach
// ---------- it throws Task not serializable Error
//    val dangerousDriver = spark.read
//      .format("csv")
//      .option("header","true")
//      .option("path","D:/Users/Meazer/data-engineer-bootcamp-assessment/data-hbase/dangerous-driver.csv")
//     .load()
    
// read the csv file using Source.fromFile
  val dangerousDriver = Source.fromFile("D:/Users/Meazer/data-engineer-bootcamp-assessment/data-hbase/dangerous-driver.csv")
    .getLines()

    dangerousDriver.foreach( (attr) => {
     table.put(buildPut(attr.mkString(",")))
    })


  table.close()
  connection.close()


// --------------- Here I tried to use spark scala connector with catalog but it throws json4s error
//    extraDriver.write.
//      options(
//      Map(HBaseTableCatalog.tableCatalog -> catalog, HBaseTableCatalog.newTable -> "5"))
//      .format("org.apache.spark.sql.execution.datasources.hbase")
//      .save()

  }
}