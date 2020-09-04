package rdd

import org.apache.spark.{SparkConf, SparkContext}


object exec {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("wordcount").setMaster("local")
    val sc = new SparkContext(conf)
    val rdd = sc.parallelize(Array(("spark", 2), ("hadoop", 6), ("hadoop", 4), ("spark", 6)))
    rdd.mapValues(x => (x, 1))
      .reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2)) // reduceByKey(func)的功能是使用func函数合并具有相同键的值
      .mapValues(x => x._1 / x._2)
      .foreach(println)
  }
}

