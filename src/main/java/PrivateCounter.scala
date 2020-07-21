class PrivateCounter {
  private var privateValue = 0
  private var name = ""

  def value = privateValue

  def value_=(newValue: Int) {
    if (newValue > 0) {
      privateValue = newValue
    } else {
      printf("%d 是负数\n", newValue)
    }
  }

  def nameValue = name

  def nameValue_=(name: String): Unit = {
    this.name = (name + "ahah")
  }

  def increment(step: Int): Unit = {
    value += step
  }

  def current = value

  def reset() { // 可以省略:Unit=
    value = 0 // 会调用setter, 除非用privateValue=0
    nameValue = "" // 会调用setter
  }
}

object PrivateCounterMain extends App {
  val pc = new PrivateCounter
  pc.value = 10
  pc.value = -2
  println(pc.current)
  pc.nameValue = "yangkai"
  println(pc.nameValue)
  pc.nameValue_=("zhidao")
  println(pc.nameValue)
  pc.reset()
  printf("value=%d, name=%s\n", pc.value, pc.nameValue)
  pc increment 100
  println(pc.current)
}