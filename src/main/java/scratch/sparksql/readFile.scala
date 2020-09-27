package scratch.sparksql

import org.apache.spark.sql.SparkSession
import scala.util.parsing.json.JSON

object readFile {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("demo1")
      .master("local[3]")
      .getOrCreate()
    val fileName = "file:///Users/admin/Documents/javaprojects/scala_demo/src/main/resources/people.json"

    val jsonStrs = spark.sparkContext.textFile(fileName)
    val result = jsonStrs.map(s => JSON.parseFull(s))
    result.foreach({ r =>
      r match {
        case Some(x: Map[String, Any]) => println(x)
        case None => println("Parsing failed")
        case other => println("Unknown data structure: " + other)
      }
    })
  }

}
