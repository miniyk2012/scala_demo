package company

import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.Base64

import com.alibaba.fastjson.{JSON, JSONArray}
import company.common.{Constant, Utils}
import company.model.SdkFilterEvent

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks


object SdkFilterParse {
  // sdk_filter_info分流类型
  val SDK_FILTER_INFO = "t=sdk_filter_info"
  val loop = new Breaks


  def getFilterDataByTraceType(raw: String, traceType: String): String = {
    var filterData: String = null
    if (!raw.contains(traceType)) {
      filterData
    }
    Constant.RGX_GET.findFirstMatchIn(raw).foreach {
      m =>
        val sub: List[String] = m.subgroups
        if (sub.length == 1) {
          val kvArray = sub.head.split('&')
          loop.breakable {
            for (kv <- kvArray) {
              val Array(k, vv) = kv.trim.split("=", 2)
              if (k == "filter") {
                filterData = vv
                loop.break
              }
            }
          }
        }
    }
    filterData
  }


  /**
   *
   * @param filterData
   * @return
   */
  def getSdkFilterInfoEventFromFilterData(filterData: String): Seq[SdkFilterEvent] = {
    def fillSdkEvents(events: ListBuffer[SdkFilterEvent], searchId: String, funnelName: String, filteredAds: JSONArray) = {
      for (i <- 0 until filteredAds.size()) {
        val sdkEvent = SdkFilterEvent()
        sdkEvent.search_id = searchId
        sdkEvent.funnel_name = funnelName
        val filteredAd = filteredAds.getJSONObject(i)
        filteredAd.entrySet().asScala.foreach(kv => {
          val k = kv.getKey()
          val v = kv.getValue().toString
          k match {
            case "ad_src" => sdkEvent.adsrc = Utils.toInt(v)
            case "ad_type" => sdkEvent.adtype = Utils.toInt(v)
            case "class_id" => sdkEvent.adclass = Utils.toLong(v)
            case "dsp_cpm" => sdkEvent.dsp_cpm = Utils.toLong(v)
            case "dsp_ecpm" => sdkEvent.dsp_ecpm = Utils.toLong(v)
            case "group_id" => sdkEvent.unit_id = Utils.toLong(v)
            case "idea_id" => sdkEvent.idea_id = Utils.toLong(v)
            case "plan_id" => sdkEvent.plan_id = Utils.toLong(v)
            case "user_id" => sdkEvent.user_id = Utils.toLong(v)
            case _ =>
          }
        })
        events.append(sdkEvent)
      }
    }

    val ret = new ListBuffer[SdkFilterEvent]
    try {
      val json: String = new String(Base64.getDecoder().decode(URLDecoder.decode(filterData.trim, "UTF-8").replace("\n", "")),StandardCharsets.UTF_8)
      val jsonobj = JSON.parseObject(json)
      val searchId = jsonobj.getString("search_id")
      val funnels = jsonobj.getJSONArray("funnels")
      for (i <- 0 until funnels.size()) {
        try {
          val funnel = funnels.getJSONObject(i)
          val funnelName = funnel.getString("name")
          val sdkFilterEvent = SdkFilterEvent()
          if (funnel.containsKey("FilteredAds")) {
            val filteredAds = funnel.getJSONArray("FilteredAds")
            fillSdkEvents(ret, searchId, funnelName, filteredAds)
          } else {
            val sdkFilterEvent = SdkFilterEvent()
            sdkFilterEvent.search_id = searchId
            sdkFilterEvent.funnel_name = funnelName
            ret.append(sdkFilterEvent)
          }
        } catch {
          case _: Throwable =>
        }
      }

    } catch {
      case _: Throwable => Seq()
    }
    ret
  }

  def main(args: Array[String]): Unit = {
    val filter_raw =
      """
        |58.246.227.82 - - [28/Sep/2020:18:25:04 +0800] "GET /bidsdk?filter=eyJmdW5uZWxzIjpbeyJGaWx0ZXJlZEFkcyI6W3siYWRfc3JjIjoiQURYIiwiYWRfdHlwZSI6IjMi%0ALCJjbGFzc19pZCI6IjEwMDEwMjEwNCIsImRzcF9jcG0iOjEyOSwiZHNwX2VjcG0iOjEyOSwiZ3Jv%0AdXBfaWQiOiIzNDAwMDQ5IiwiaWRlYV9pZCI6IjcxMzU0OTciLCJwbGFuX2lkIjoiMjg1MjY1OSIs%0AInVzZXJfaWQiOiIxNzU5Nzk4In1dLCJmaWx0ZXJfYWRfbnVtIjowLCJpbnB1dF9hZF9udW0iOjEs%0AIm5hbWUiOiJTZGtCZWZvcmVSYW5rTWFyayIsIm91dHB1dF9hZF9udW0iOjF9LHsiZmlsdGVyX2Fk%0AX251bSI6MCwiaW5wdXRfYWRfbnVtIjoxLCJuYW1lIjoiU2RrUmFua1RydW5jYXRlRmlsdGVyIiwi%0Ab3V0cHV0X2FkX251bSI6MX0seyJGaWx0ZXJlZEFkcyI6W3siYWRfc3JjIjoiQURYIiwiYWRfdHlw%0AZSI6IjMiLCJjbGFzc19pZCI6IjEwMDEwMjEwNCIsImRzcF9jcG0iOjEyOSwiZHNwX2VjcG0iOjEy%0AOSwiZ3JvdXBfaWQiOiIzNDAwMDQ5IiwiaWRlYV9pZCI6IjcxMzU0OTciLCJwbGFuX2lkIjoiMjg1%0AMjY1OSIsInVzZXJfaWQiOiIxNzU5Nzk4In1dLCJmaWx0ZXJfYWRfbnVtIjowLCJpbnB1dF9hZF9u%0AdW0iOjEsIm5hbWUiOiJTZGtBZnRlclJhbmtUcnVuY2F0ZU1hcmsiLCJvdXRwdXRfYWRfbnVtIjox%0AfV0sInNlYXJjaF9pZCI6InJKUmhiOFE5N1ZIaW4wQW9uTld4bGRaVl9CSEtfczVyanVsaVZrM1ci%0AfQ%3D%3D%0A&opt_ab_test_id=&opt_ab_trace=&opt_androidid=cb35384511741b74&opt_app_pkgid=com.iclicash.adv&opt_app_vc=1&opt_app_vn=2.840-b1&opt_bootstrap_v=&opt_brand=xiaomi&opt_ideaid=0&opt_imei=867183042005393&opt_imsi=460091552007757&opt_meid=&opt_model=Redmi+7&opt_nettype=NETTYPE_WIFI&opt_os_version=9&opt_v=2.840-b1&os=Android&sh=1369&sw=720&t=sdk_filter_info&timestamp=1601288708787&v=1.6.0 HTTP/1.1" 200 0 "-" "Dalvik/2.1.0 (Linux; U; Android 9; Redmi 7 MIUI/V11.0.4.0.PFLCNXM)" "58.246.227.82" "-" 0.000 "tuid=-" "tkid=-"
        |""".stripMargin
    val other_raw =
      """
        |183.220.74.172 - - [28/Sep/2020:18:56:59 +0800] "GET /bidsdk?gdt_ad_num=0&iclicashsid=OWM4NTVjMWM3NmRlMmVmYzcyMjYwLjU4Ni0yMDUt&is_background=0&is_cache=0&opt_ab_test_id=&opt_ab_trace=0&opt_ad_context=&opt_androidid=1c0da6da8741a531&opt_app_pkgid=com.jifen.qukan&opt_app_vc=30997000&opt_app_vn=3.9.97.000.0923.1549&opt_bootstrap_v=&opt_brand=xiaomi&opt_channel=&opt_current_index=0&opt_current_page=0&opt_dsp_bidding_mode=rtb&opt_dsp_sdk_version=2.840&opt_dsp_slotid=7960719&opt_exp_id=1%2C67782%2C79562%2C82572%2C82731&opt_ideaid=0&opt_imei=863542043255998&opt_imsi=460022083440202&opt_limit_price=0&opt_mediaid=80000001&opt_meid=&opt_memberid=467678709&opt_model=MI+6X&opt_nettype=NETTYPE_WIFI&opt_oaid=787ded6473733825&opt_orientation=0&opt_os_version=9&opt_report_type=0&opt_sdk_adtype=3&opt_searchid=OWM4NTVjMWM3NmRlMmVmYzcyMjYwLjU4Ni0yMDUt&opt_seq_id=1&opt_slotid=7960719&opt_src=ADX&opt_unique_slotid=null&opt_v=2.840&os=Android&sh=2030&sw=1080&t=dsp_reqid&timestamp=1601290618458&toutiao_ad_num=0&v=1.6.0 HTTP/1.1" 200 0 "-" "Dalvik/2.1.0 (Linux; U; Android 9; MI 6X MIUI/V11.0.6.0.PDCCNXM)" "183.220.74.172" "-" 0.000 "tuid=TTGseFygfvED7O15UMfpMA" "tkid=ACFNMax4XKB-8QPs7XlQx-kwSaqdBisW1_c0NzUxNDk1MDg5NTIyNQ"
        |""".stripMargin
    val filterData1 = getFilterDataByTraceType(filter_raw, SDK_FILTER_INFO)
    val filterData2 = getFilterDataByTraceType(other_raw, SDK_FILTER_INFO)
    println(filterData1)
    println(filterData2)
    val sdkEvents = getSdkFilterInfoEventFromFilterData(filterData1)
    println(sdkEvents)
  }
}
