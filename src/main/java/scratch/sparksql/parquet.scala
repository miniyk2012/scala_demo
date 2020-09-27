package scratch.sparksql

import org.apache.spark.sql.SparkSession

object parquet {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("demo1")
      .master("local[3]")
      .getOrCreate()
    val fileName = "file:///Users/admin/Documents/javaprojects/scala_demo/src/main/resources/people.json"
    val df = spark.read.json(fileName)
    df.write.parquet("file:///Users/admin/Documents/javaprojects/scala_demo/src/main/resources/newpeople.parquet")
    val parquetFileDF =
      spark.read.parquet("file:///Users/admin/Documents/javaprojects/scala_demo/src/main/resources/newpeople.parquet")
    parquetFileDF.printSchema()
    parquetFileDF.show()
    parquetFileDF.select(parquetFileDF("name"), (parquetFileDF("age") + 1).as("plusone_age")).show()
    spark.stop()
  }
}
