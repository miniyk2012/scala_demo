package sparksql

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object dataframe_demo2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("demo1")
      .master("local[3]")
      .getOrCreate()
    val fileName = "file:///Users/admin/Documents/javaprojects/scala_demo/src/main/resources/people.txt"
    val schemaString = "name age"
    val fields = schemaString.split(" ").map(fieldName => StructField(fieldName, StringType, nullable = true))
    val schema = StructType(fields)
    val peopleRDD = spark.sparkContext.textFile(fileName)
    val rowRDD = peopleRDD.map(_.split(",")).map(attributes => Row(attributes(0), attributes(1).trim))
    val peopleDF = spark.createDataFrame(rowRDD, schema)
    //必须注册为临时表才能供下面查询使用
    peopleDF.createOrReplaceTempView("people")
    val results = spark.sql("SELECT name,age FROM people")
    results.foreach(row=>println(row(0)))
    results.show()
  }
}

