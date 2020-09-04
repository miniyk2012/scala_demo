package wordcount

import org.apache.spark.{SparkConf, SparkContext}
import utils.Utils

import scala.io.Source


object WordCount {
  def readFile(sc: SparkContext): Unit = {
    val inputFile = "/student.txt"
    val outputFile = "student_writeback.txt"
    val textFile = sc.textFile(this.getClass.getResource(inputFile).toString)
    Utils.removeFile(this.getClass.getResource("/").getPath + outputFile)
    textFile.saveAsTextFile(this.getClass.getResource("/").toString + outputFile)
    val content: String = Source.fromURL(this.getClass.getResource("/").toString + outputFile).getLines().mkString("\n")
    println(content)
    // 把刚保存的数据再读出来
    val textFile2 = sc.textFile(this.getClass.getResource("/").toString + outputFile)
    textFile2.foreach(println)
  }

  def readHdfsFile(sc: SparkContext): Unit = {
    val textFile = sc.textFile("hdfs://zjka-cpc-backend-bigdata-qa-01:9000/user/hadoop/word.txt")
    println(textFile.first())
  }

  def wordCount(sc: SparkContext): Unit = {
    val inputFile = "hdfs://zjka-cpc-backend-bigdata-qa-01:9000/user/hadoop/word.txt"
    val textFile = sc.textFile(inputFile)
    val wordCount = textFile.flatMap(line => line.split(" ")).map(word => (word, 1))
      .reduceByKey((a, b) => a + b)
    wordCount.cache()
    wordCount.foreach(println)
  }

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("WordCount")
//      .setMaster("local")
//      .setMaster("spark://zjka-cpc-backend-bigdata-qa-01:7077")
      .setMaster("yarn")
    val sc = new SparkContext(conf)
//    wordCount(sc)
//    readFile(sc)
//    readHdfsFile(sc)
    wordCount(sc)
    Thread.sleep(1000000)
    sc.stop()
  }
}
