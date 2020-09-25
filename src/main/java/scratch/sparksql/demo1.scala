package sparksql

import org.apache.spark.sql.SparkSession

object demo1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("demo1")
      .master("local[3]")
      .getOrCreate()
    val fileName = "file:///Users/admin/Documents/javaprojects/scala_demo/src/main/resources/people.json"
    val df = spark.read.json(fileName)
    df.show()
    // 打印模式信息
    df.printSchema()
    // 选择多列
    df.select(df("name"), (df("age") + 1).as("plusone_age")).show()
    // 条件过滤
    df.filter(df("age") > 20).show()
    // 分组聚合
    df.groupBy("age").count().show()
    // 排序
    df.sort(df("age").desc).show()
    //多列排序
    df.sort(df("age").desc, df("name").asc).show()
    // 对列进行重命名
    df.select(df("name").as("username"), df("age")).show()
  }

}
