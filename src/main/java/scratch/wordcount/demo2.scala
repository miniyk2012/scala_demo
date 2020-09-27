package scratch.wordcount

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 找出文本文件中单行文本所包含的单词数量的最大值
 */
object demo2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("maxLength").setMaster("local")
    val sc = new SparkContext(conf)
    val lines = sc.textFile("file:///Users/admin/Documents/javaprojects/scala_demo/src/main/resources/data.txt")
    val maxSize = lines.map(_.split(" ").length).reduce((a, b) => {
      if (a > b) a else b
    })
    println(maxSize)

    val list = Array("Hadoop", "Spark", "Flink")
    val rdd = sc.parallelize(list)
    rdd.cache()
    println(rdd.count())
    println(rdd.collect().mkString(","))
  }
}
