import java.util.Date

def log(date: Date, message: String): Unit = {
  println(date.toString + message)
}

def log2(date:Date)(message:String): Unit = {
  println(date.toString + message)
}

val todayLog = log(new Date(), _:String)
todayLog("hello")

val todayLog2 = log2(new Date()) _
todayLog2(" wahy")