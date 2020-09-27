package scratch.rdd

import org.apache.spark.{SparkConf, SparkContext}


object d2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("scratch/wordcount").setMaster("local")
    val sc = new SparkContext(conf)
    val pairRDD1 = sc.parallelize(Array(("spark", 1), ("spark", 2), ("hadoop", 3), ("hadoop", 5)))
    val pairRDD2 = sc.parallelize(Array(("spark", "fast"), ("spark", "nunu"), ("hadoop", "hah")))
    val result = pairRDD1.join(pairRDD2)
    result.foreach(println)
  }
}
