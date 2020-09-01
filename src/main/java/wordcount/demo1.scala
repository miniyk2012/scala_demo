package wordcount

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object demo1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("WordCount").setMaster("local")
    val sc = new SparkContext(conf)
    val lines = sc.textFile("file:///Users/admin/Documents/javaprojects/scala_demo/src/main/resources/student.txt")
    val lineLengths = lines.map(s => s.length)
    val totalLength = lineLengths.reduce((a, b) => a + b)
    println(lineLengths.collect().foreach(println))
    println(lines.filter(line => line.contains("male")).count())
    println(totalLength)
  }
}
