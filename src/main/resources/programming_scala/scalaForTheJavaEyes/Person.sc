
class Person(val firstName: String, val lastName: String) {
  var position: String = _
  var age: Int=10
  println("create " + toString)

  def this(firstName: String, lastName: String, hold: String) {
    this(firstName, lastName)
    position = hold
  }

  override def toString(): String = {
    firstName + " " + lastName + " " + position
  }
}

val yk = new Person("yang", "kai", "sh")
println(yk)
val wzm = new Person("wang", "zheming")
println(wzm)