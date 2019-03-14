import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

object DriverAgg {
  def main(args: Array[String]) {

    val spark = SparkSession.builder
      .master("local")
      .appName("Drivers")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()
    // =========================== Step 1 ==================================
    val driversDf = spark.read
      .format("csv")
      .option("header","true")
      .option("path","D:/Users/Meazer/data-engineer-bootcamp-assessment/data-spark/drivers.csv")
      .load()
    val timesheetDf = spark.read
      .format("csv")
      .option("header","true")
      .option("path","D:/Users/Meazer/data-engineer-bootcamp-assessment/data-spark/timesheet.csv")
      .load()

    val df1 = driversDf.select("driverId", "name")

    val df2 = timesheetDf.groupBy("driverId").agg( sum("hours-logged"),sum("miles-logged"))

    val joined = df1.join(df2, df1("driverId") === df2("driverId"))

    joined.show(40, false)

    // ============================= End Step 1============================

  }

}

