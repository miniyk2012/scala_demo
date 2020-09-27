package scratch.exercise.ch02

object Series extends App {
  def sum(q: Int): Double = {
    var s = 0D
    var n = 1
    while (s < q) {
      s += (n+1) * 1.0 / n
      n += 1
    }
    s
  }

  println(sum(50))
  println(sum(1))
  println(sum(30))
}
