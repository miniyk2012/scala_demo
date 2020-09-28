package company.common

import java.text.SimpleDateFormat
import java.util.Calendar

import scala.collection.mutable.ListBuffer

object Utils {
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

  def main(args: Array[String]): Unit = {
    val beginCal: Calendar = DateUtils.getCalendarByDateTimeStr("2020-09-20 00:00:00")
    val filter = Utils.getFilterCondition(beginCal, 10);
    println(filter)
  }
}
