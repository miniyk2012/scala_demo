package basic

class Temp {
  var x: Int = 0 //这里使用x作为字段名
  def x(i: Int): Int = x + i //这里又使用了同名的x作为方法名
}

object Func {
  def sumPowersOfTwo(a: Int, b: Int): Int = {
    def powerOfTwo(x: Int): Int = {
      if (x == 0) 1 else 2 * powerOfTwo(x - 1)
    }
    if (a > b) 0 else powerOfTwo(a) + sumPowersOfTwo(a + 1, b)
  }
}

object TempMain extends App {
  val t = new Temp
  t.x = 100
  println(t.x(10))

  println(Func.sumPowersOfTwo(1,2))
}