package wordcount

import org.apache.spark.{SparkConf, SparkContext}

object demo3 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("wordcount").setMaster("local")
    val sc = new SparkContext(conf)
    val lines = sc.textFile("file:///Users/admin/Documents/javaprojects/scala_demo/src/main/resources/data.txt")
    val pairRDD = lines.flatMap(line => line.split(" ")).map(word => (word,1))
    pairRDD.cache()
    val accumulatedRdd = pairRDD.reduceByKey((a,b)=>a+b)
    accumulatedRdd.collect().foreach(println)
    println()
    println(pairRDD.groupByKey().foreach(println))
    println("keys")
    pairRDD.keys.foreach(println)
    println("values")
    pairRDD.values.foreach(println)
    println("sortByKey")
    accumulatedRdd.sortByKey().foreach(println)
    println("mapValues")
  }
}
