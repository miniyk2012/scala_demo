package sparksql

import org.apache.spark.sql.SparkSession

object demo1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().getOrCreate()
    val df = spark.read.json("file:///Users/admin/Documents/javaprojects/scala_demo/src/main/resources/people.json")
    df.show()
  }

}
