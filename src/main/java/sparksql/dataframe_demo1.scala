package sparksql

import org.apache.spark.sql.SparkSession

object dataframe_demo1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("demo1")
      .master("local[3]")
      .getOrCreate()
    val fileName = "file:///Users/admin/Documents/javaprojects/scala_demo/src/main/resources/people.txt"
    val peopleRdd = spark.sparkContext.textFile(fileName).map(_.split(","))
      .map(attributes => Person(attributes(0), attributes(1).trim.toInt))
    // RDD转化为DataFrame
    val peopleDF = spark.createDataFrame(peopleRdd)
    peopleDF.createOrReplaceTempView("people")  //必须注册为临时表才能供下面的查询使用
    val selected = spark.sql("select name,age from people where age > 20") //最终生成一个DataFrame
    selected.show()
  }
}


case class Person(name: String, age: Long) //定义一个case class