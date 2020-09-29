package company.common

import scala.util.matching.Regex

object Constant {
  // 日期格式
  val DATETIME_FMT = "yyyy-MM-dd HH:mm:ss"
  // 日期格式
  val DAY_FMT: String = "yyyy-MM-dd"
  // 日期格式
  val DHM_FMT: String = "yyyy-MM-dd/HH/mm"
  // 小时格式
  val HH_FMT: String = "HH"
  // 分钟格式
  val MM_FMT: String = "mm"
  // 用于解析sdk数据的正则表达式
  val RGX_GET: Regex = """GET\s/bidsdk\?([^\s]+)""".r
}
