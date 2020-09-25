package sparksql

import org.apache.spark.sql.SparkSession

object hive_demo {
  def main(args: Array[String]): Unit = {
    val warehouseLocation = "spark-warehouse"
    val spark = SparkSession.builder()
      .appName("Spark Hive Example")
      .master("local")
//      .config("spark.sql.warehouse.dir", warehouseLocation)
      .config("hive.metastore.uris", "thrift://zjka-cpc-backend-bigdata-qa-01:9083")
      .enableHiveSupport()
      .getOrCreate()
    spark.sql("SELECT * FROM sparktest.student")
  }

}
