package company.model

case class SdkFilterEvent(
                           var search_id: String = "",
                           var funnel_name: String = "",
                           var unit_id: Long = 0L,
                           var idea_id: Long = 0L,
                           var plan_id: Long = 0L,
                           var user_id: Long = 0L,
                           var adtype: Int = 0,
                           var adclass: Long = 0L,
                           var adsrc: Int = 0,
                           var dsp_cpm: Long = 0L,
                           var dsp_ecpm: Long = 0L,
                           var minute: String = "",
                           var day: String = "",
                           var hour: String = "",
                           var mm: String = ""
                         )
