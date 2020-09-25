package spark_streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 文件流
 */
object demo1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("TestDStream").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(20))
    val lines =
      ssc.textFileStream("file:///Users/admin/Documents/javaprojects/scala_demo/src/main/resources")
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    wordCounts.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
