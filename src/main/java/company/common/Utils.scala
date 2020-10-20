package company.common

import java.net.{InetAddress, URI}
import java.sql.{Connection, DriverManager, Statement}
import java.text.SimpleDateFormat
import java.util.{Calendar, Properties}

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.permission.FsPermission
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql._
import org.apache.spark.sql.functions.udf

import scala.collection.mutable.ListBuffer
import scala.sys.process._

object Utils {

  // ok文件的位置
  val DIR_OK_FILE: String = "hdfs://cpc1/home/successflag/cpcdept/%s"

  // ok文件的格式
  val FMT_OK_FILE: String = "hdfs://cpc1/home/successflag/cpcdept/%s/%s.%s-%s-%s.ok"

  // 需要回刷的分区数，where过滤条件
  val FMT_TABLE_DHM: String = s"(`day`='%s' and `hour`=%s and `minute`=%s)"

  /**
   * 回刷数据时，根据传入开始时间、需要回刷的分区数(min级别),返回获取log表数据的where过滤条件
   *
   * @param cal
   * @param numPartitionsToWrite
   * @return
   */
  def getFilterCondition(cal: Calendar, numPartitionsToWrite: Int): String = {
    val dateHourList: ListBuffer[String] = ListBuffer[String]()
    val calToGo: Calendar = Calendar.getInstance()
    calToGo.setTimeInMillis(cal.getTimeInMillis)

    for (_ <- 0 until numPartitionsToWrite) {
      dateHourList += FMT_TABLE_DHM.format(
        new SimpleDateFormat(Constant.DAY_FMT).format(calToGo.getTime),
        new SimpleDateFormat(Constant.HH_FMT).format(calToGo.getTime),
        new SimpleDateFormat(Constant.MM_FMT).format(calToGo.getTime)
      )
      calToGo.add(Calendar.MINUTE, 1)
    }
    "(" + dateHourList.mkString(" or ") + ")"
  }


  /**
   * 计算两个cal相差的分钟数
   *
   * @param beginCal
   * @param endCal
   * @return
   */
  def getNumPartitionsToWrite(beginCal: Calendar, endCal: Calendar): Int = {
    var numPartitionsToWrite: Int = 10
    if (endCal != null) {
      numPartitionsToWrite = (numPartitionsToWrite + ((endCal.getTimeInMillis - beginCal.getTimeInMillis) / (1000 * 60)).asInstanceOf[Int])
    }
    numPartitionsToWrite
  }


  def LongToIPv4(ip: Long): String = {
    val bytes: Array[Byte] = new Array[Byte](4)
    bytes(0) = ((ip & 0xff000000) >> 24).toByte
    bytes(1) = ((ip & 0x00ff0000) >> 16).toByte
    bytes(2) = ((ip & 0x0000ff00) >> 8).toByte
    bytes(3) = (ip & 0x000000ff).toByte
    InetAddress.getByAddress(bytes).getHostAddress
  }

  def toInt(s: String): Int = try s.trim.toInt catch { case _: Exception => 0: Int }
  def toBoolean(s: String): Boolean = try s.trim.toBoolean catch { case _: Exception => false:Boolean }

  def toLong(s: String): Long = try s.trim.toLong catch { case _: Exception => 0L }

  def toAutoInt(s: String): Int = try if (s.indexOf("1") >= 0) 1 else s.trim.toInt catch { case e: Exception => 0 }

  def toFloat(s: String): Float = try s.trim.toFloat catch { case _: Exception => 0: Float }

  def getDateHourMinuteForPartition(cal: Calendar, minuteDuration: Int): Array[Array[String]] = {
    val calToGo: Calendar = Calendar.getInstance()
    calToGo.setTimeInMillis(cal.getTimeInMillis)

    val parts = new Array[Array[String]](minuteDuration)
    for (m <- 0 until minuteDuration) {
      parts(m) = new SimpleDateFormat(Constant.DHM_FMT).format(calToGo.getTime).split("/")
      calToGo.add(Calendar.MINUTE, 1)
    }
    parts
  }


  def generateSuccessFlag(dbnameToGo: String, eventTableName: String, cal: Calendar, partitionToWrite: Array[String]): Any = {
    val dateFormat = new SimpleDateFormat(Constant.DAY_FMT)
    val dateDirectory: String = DIR_OK_FILE.format(dateFormat.format(cal.getTime))
    val checkIfDirectoryExists: Int = s"hadoop fs -test -e %s".format(dateDirectory) !

    if (checkIfDirectoryExists != 0) {
      s"hadoop fs -mkdir %s; hadoop fs -chmod -R 755 %s".format(dateDirectory, dateDirectory) !
    }

    val okFileName = FMT_OK_FILE
      .format(
        dateFormat.format(cal.getTime),
        dbnameToGo,
        eventTableName,
        partitionToWrite(1), // hourToGo
        partitionToWrite(2) // minuteToGo
      )
    s"hadoop fs -touchz %s; hadoop fs -chmod -R 755 %s".format(okFileName, okFileName) !
  }

  // 减少1个参数的generateSuccessFlag方法
  def generateSuccessFlag(dbnameToGo: String, eventTableName: String, beginCal: Calendar): Unit = {
    val currentDate: String = new SimpleDateFormat(Constant.DAY_FMT).format(beginCal.getTime)
    val currentHour: String = new SimpleDateFormat(Constant.HH_FMT).format(beginCal.getTime)
    val currentMinute: String = new SimpleDateFormat(Constant.MM_FMT).format(beginCal.getTime)

    val dateDirectory: String = DIR_OK_FILE.format(currentDate)
    val checkIfDirectoryExists: Int = s"hadoop fs -test -e %s".format(dateDirectory) !

    if (checkIfDirectoryExists != 0) {
      s"hadoop fs -mkdir %s; hadoop fs -chmod -R 755 %s".format(dateDirectory, dateDirectory) !
    }

    val okFileName: String = FMT_OK_FILE.format(currentDate, dbnameToGo, eventTableName, currentHour, currentMinute)
    s"hadoop fs -touchz %s".format(okFileName) !

    s"hadoop fs -chmod -R 755 %s".format(okFileName) !
  }

  /**
   * 根据传入分区partitionsToWrite，生成ok文件
   *
   * @param dbnameToGo
   * @param eventTableName
   * @param beginCal
   * @param partitionsToWrite
   */
  def generateSuccessFlag(dbnameToGo: String, eventTableName: String, beginCal: Calendar, partitionsToWrite: Array[Array[String]]): Unit = {
    for (element <- partitionsToWrite) {
      if (element(2).endsWith("0")) {
        val dateDirectory: String = "hdfs://cpc1/home/successflag/cpcdept/%s".format(element(0))
        val fsParent: FileSystem = FileSystem.get(URI.create("hdfs://cpc1/home/successflag/cpcdept/"), new Configuration())
        val checkIfDirectoryExists: Int = s"hadoop fs -test -e %s".format(dateDirectory) !

        if (checkIfDirectoryExists != 0) {
          val dateDirectoryPath = new Path(dateDirectory)
          fsParent.mkdirs(dateDirectoryPath)
          fsParent.setPermission(dateDirectoryPath, new FsPermission("755"))
        }
        val fs: FileSystem = FileSystem.get(URI.create(dateDirectory), new Configuration())
        val okFileNamePath = new Path("hdfs://cpc1/home/successflag/cpcdept/%s/%s.%s-%s-%s.ok".format(element(0), dbnameToGo, eventTableName, element(1), element(2)))
        fs.create(okFileNamePath)
        fs.setPermission(okFileNamePath, new FsPermission("755"))
      }
    }
  }


  def prepareQueryResultForTridentWithSelectKeys(spark: SparkSession, keys: String, databaseName: String, tableName: String, cal: Calendar, minuteDuration: Int): Dataset[Row] = {
    spark.sql(s"select %s from %s.%s where %s".format(keys, databaseName, tableName, Utils.getFilterCondition(cal, minuteDuration)))
  }

  // fym 190428.
  def getSelectedHoursBefore(date: String, hour: String, hours: Int): String = {
    val dateHourList = ListBuffer[String]()
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val cal = Calendar.getInstance()
    cal.set(date.substring(0, 4).toInt, date.substring(5, 7).toInt - 1, date.substring(8, 10).toInt, hour.toInt, 0, 0)
    for (t <- 0 to hours) {
      if (t > 0) cal.add(Calendar.HOUR, -1)
      val formatDate = dateFormat.format(cal.getTime)
      val datee = formatDate.substring(0, 10)
      val hourr = formatDate.substring(11, 13)

      val dateL = s"(`day`='$datee' and `hour`='$hourr')"
      dateHourList += dateL
    }

    "(" + dateHourList.mkString(" or ") + ")"
  }

  def getSelectedDaysBefore(date: String, days: Int): String = {
    val cal: Calendar = Calendar.getInstance()
    cal.set(date.substring(0, 4).toInt, date.substring(5, 7).toInt - 1, date.substring(8, 10).toInt, 0, 0, 0)
    val dateHourList: Seq[String] = for (t <- 0 to days) yield {
      if (t > 0) cal.add(Calendar.DAY_OF_MONTH, -1)
      s"(`day` = '%s')".format(new SimpleDateFormat(Constant.DAY_FMT).format(cal.getTime))
    }
    "(" + dateHourList.mkString(" or ") + ")"
  }

  // fym 190428.
  def getSelectedHoursAndSelectedTableNameBefore(date: String, hour: String, table: String, hours: Int): String = {
    val dateHourList = ListBuffer[String]()
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val cal = Calendar.getInstance()
    cal.set(date.substring(0, 4).toInt, date.substring(5, 7).toInt - 1, date.substring(8, 10).toInt, hour.toInt, 0, 0)
    for (t <- 0 to hours) {
      if (t > 0) {
        cal.add(Calendar.HOUR, -1)
      }
      val formatDate = dateFormat.format(cal.getTime)
      val datee = formatDate.substring(0, 10)
      val hourr = formatDate.substring(11, 13)

      val dateL = s"($table.`day`='$datee' and $table.`hour`='$hourr')"
      dateHourList += dateL
    }

    "(" + dateHourList.mkString(" or ") + ")"
  }

  // fym 190505.
  def insertIntoHdfsAndAddPartitionsDaily(
                                           spark: SparkSession,
                                           df: DataFrame,
                                           date: String,
                                           dbname: String,
                                           table: String
                                         ): Unit = {
    df.write.partitionBy("day").mode(SaveMode.Append).parquet(s"hdfs://emr-cluster2/warehouse/$dbname.db/$table")

    spark.sql(
      s"""
         |ALTER TABLE %s.%s
         | add if not exists PARTITION(`day` = "$date")
         | LOCATION 'hdfs://emr-cluster2/warehouse/$dbname.db/$table/day=$date/'
       """
        .format(dbname, table)
        .stripMargin
        .trim
    )
  }

  // fym 190509.
  def SetUserProfileTagInHiveDaily(in: RDD[(String, Int, Boolean)]): Array[(String, Int)] = {
    val cal = Calendar.getInstance()
    val date = new SimpleDateFormat("yyyy-MM-dd")
      .format(cal.getTime())

    val spark = SparkSession
      .builder()
      .getOrCreate()
    import spark.implicits._

    val ft = in
      .map(x => x._2)
      .distinct()
      .toLocalIterator

    val rs = ft.map { tag =>
      in
        .filter(_._2 == tag)
        .map { x => (x._1, x._3) }
        .toDF("uid", "operation")
        .coalesce(20)
        .write
        .mode(SaveMode.Append)
        .parquet("hdfs://emr-cluster/warehouse/dl_cpc.db/cpc_userprofile_tag_daily/%s/%s".
          format(date, tag))

      val sql =
        """
          |ALTER TABLE dl_cpc.cpc_userprofile_tag_daily add if not exists PARTITION (`date` = "%s" , `tag` = "%s")
          |LOCATION 'hdfs://emr-cluster/warehouse/dl_cpc.db/cpc_userprofile_tag_daily/%s/%s'
        """
          .stripMargin
          .format(date, tag, date, tag)

      //在分区被未知任务指向错误路径后，删除错误分区
      val sql_2 =
        """
          |ALTER TABLE dl_cpc.cpc_userprofile_tag_daily  drop if exists PARTITION(`date` = "%s" , `tag` = "%s")
        """
          .stripMargin
          .format(date, tag)

      spark.sql(sql_2)
      spark.sql(sql)

      (sql, in.filter(_._2 == tag).count().toInt)
    }
    // rs.foreach(println)
    rs.toArray
  }

  def filterAndInsertRDD(data: DataFrame, table: String): Unit = {
    data.write.mode(SaveMode.Append).insertInto(s"$table")
  }

  def murmurHash64 = udf { uniqKey: String => {Murmur3Hash.stringHash64(uniqKey, 0) } }

  def filterMapStringString = udf { (map : Map[String, String]) => { if (map != null && map.nonEmpty) map.filter((_: (String, String))._1 != null) else null }}

  def clearMysqlTableDateHour(url: String, props: Properties, table: String, date: String, hour: String): Unit = {
    try {
      Class.forName(props.getProperty("driver"))
      val conn: Connection = DriverManager.getConnection(url, props.getProperty("user"), props.getProperty("password"))
      val stmt: Statement = conn.createStatement()
      stmt.executeUpdate(s"""delete from $table where `date` = "$date" and `hour` = $hour""")
    } catch { case _: Exception => }
  }

  def main(args: Array[String]): Unit = {
    val beginCal: Calendar = DateUtils.getCalendarByDateTimeStr("2020-09-20 00:00:00")
    val filter = Utils.getFilterCondition(beginCal, 10)
    println(filter)
  }
}
