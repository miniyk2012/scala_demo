package company.common

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

object DateUtils {

  /**
   * 传入一个字符串日期时间，返回calender实例，"2019-05-28 12:10:00"
   *
   * @param dateTimeStr
   * @return
   */
  def getCalendarByDateTimeStr(dateTimeStr: String): Calendar = {
    val dateTime: Date = new SimpleDateFormat(Constant.DATETIME_FMT).parse(dateTimeStr)
    val cal: Calendar = Calendar.getInstance()
    cal.setTime(dateTime)
    cal
  }

  /**
   * 传入日期、小时、分钟，返回秒
   * @param day
   * @param hour
   * @param minute
   */
  def getSecond(day: String, hour: String, minute: String): Long = {
    //注意SimpleDateFormat线程安全问题
    new SimpleDateFormat("yyyy-MM-ddHHmm").parse(day + hour + minute).getTime/1000
  }

}
