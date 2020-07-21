class Counter {
  var value: Int = 0


  def increment(step: Int): Unit = {
    value += step
  }

  def current = {
    value
  }

}

object CounterMain extends App {
  val myCounter = new Counter
  myCounter.value = 10
  myCounter.increment(3) //调用方法
  println(myCounter.current)
}