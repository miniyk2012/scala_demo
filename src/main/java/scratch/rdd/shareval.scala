package scratch.rdd

import org.apache.spark.{SparkConf, SparkContext}

object shareval {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("scratch/wordcount").setMaster("local")
    val sc = new SparkContext(conf)
    val broadcastVar = sc.broadcast(Array(1, 2, 3))
    broadcastVar.value.foreach(println)

    val accum = sc.longAccumulator("My Accumulator")
    sc.parallelize(Array(1, 2, 3, 4)).foreach(x => accum.add(x))
    println(accum.value)
  }

}
