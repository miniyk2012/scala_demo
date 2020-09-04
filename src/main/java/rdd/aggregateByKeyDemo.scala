package rdd

import org.apache.spark.{SparkConf, SparkContext}

/**
 * https://www.jianshu.com/p/09912beb1350
 */
object aggregateByKeyDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("aggregateByKeyDemo").setMaster("local")
    val sc = new SparkContext(conf)
    //  (用户名,(访问时间,访问页面url))
    val data = sc.parallelize(
      List(
        ("13909029812", ("20170507", "http://www.baidu.com")), ("18089376778", ("20170401", "http://www.google.com")), ("18089376778", ("20170508", "http://www.taobao.com")), ("13909029812", ("20170507", "http://www.51cto.com"))
      )
    )

    val result = data.aggregateByKey(scala.collection.mutable.Set[(String, String)]())((set, item) => {
      set += item
    }, (set1, set2) => set1 union set2).mapValues(x => x.toIterable).collect
    result.foreach(println)

  }
}
