class Car(name: String) {
  def info(): Unit = {
    println("car name is " + name)
  }
}

object Car {
  def apply(name: String): Car = new Car(name)
}

object MyTestApply {
  def main(args: Array[String]): Unit = {
    val myCar = Car("BMW")
    myCar.info()

    val array = new Array[String](3)
    for (i <- array) {
      println(i)
    }
    array(0) = "bigdata"
    array.update(1, "niubi")
    for (i <- array) {
      println(i)
    }

  }
}

