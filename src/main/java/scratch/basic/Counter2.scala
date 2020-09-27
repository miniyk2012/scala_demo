package scratch.basic

class Counter2 {
  private var value=0
  private var name = ""
  private var step = 1
  println("the main constructor")

  def this(name: String) {
    this() // 调用主构造器
    this.name = name
    println("the first")
  }

  def this(name: String, step: Int) {
    this(name)
    this.step = step
    println("the second")
  }

}



object Counter2Main extends App {
  val c1 = new Counter2()
  println()
  val c2 = new Counter2("yangkai")
  println()
  val c3 = new Counter2("yangkai", 3)
}
