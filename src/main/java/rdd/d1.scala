package rdd

import org.apache.spark.{SparkConf, SparkContext}


object d1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("wordcount").setMaster("local")
    val sc = new SparkContext(conf)
    val lines = sc.textFile("hdfs://zjka-cpc-backend-bigdata-qa-01:9000/user/hadoop/word.txt")
    val pairRDD = lines.flatMap(line => line.split(" ")).filter(word=>word!="").map(word => (word, 1))
    pairRDD.cache()
    val accumulatedRdd = pairRDD.reduceByKey((a, b) => a + b)
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
    pairRDD.mapValues(_+1).foreach(println)
  }
}

