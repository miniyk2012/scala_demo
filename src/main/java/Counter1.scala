class Counter1(name: String) {
  private var value = 0
  def increment(step: Int): Unit = { value += step}
  def current(): Int = {value}
  def info(): Unit = {printf("Name:%s",name)}
}

object Counter1Main {
  def add = (x: Int, y: Int) => x + y  //匿名函数
  def main(args: Array[String]): Unit = {
    val c = new Counter1("啊")
    c.info()
    println(add(4,6))
    println(add.apply(4,6))
//    val array = Array(1,2,3)
    val array = new Array[String](3)
    println(array)
    for (i <- array) {
      println(i)
    }
  }
}
