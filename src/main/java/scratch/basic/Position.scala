package basic

class Position(var x: Double = 0, var y: Double = 0) {
  def move(deltaX: Double = 0, deltaY: Double = 0) {
    x += deltaX
    y += deltaY
  }
}

object X {
  def main(args: Array[String]): Unit = {
    val p = new Position()
    p.move(deltaY = 5) //延y轴移动5个单位，deltaX采用了默认值
    println(p.x, p.y)
  }
}
