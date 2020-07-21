class Counter1(name: String) {
  private var value = 0
  def increment(step: Int): Unit = { value += step}
  def current(): Int = {value}
  def info(): Unit = {printf("Name:%s",name)}
}

object Counter1Main {
  def main(args: Array[String]): Unit = {
    val c = new Counter1("啊")
    c.info()
  }
}
