package inherit

abstract class Car(val name: String) {
  val carBrand: String
  var age: Int = 10
  printf("base Car %s\n", name)
  def info()

  def greeting(): Unit = {
    println("Welcome to my car!")
  }

  def this(name: String, age: Int) {
    this(name)
    this.age = age
  }
}


class BMWCar(name: String) extends Car(name) {
  override val carBrand: String = "BMW"

  override def info(): Unit = {
    printf("This is a %s car. It has been used for %d year.\n", carBrand, age)
  }

  override def greeting(): Unit = {
    println("Welcome to my BMW car! " + this.name)
  }
}

class BYDCar(override val name: String, age: Int) extends Car(name) {
  override val carBrand: String = "BYD"

  override def info(): Unit = {
    printf("This is a %s car. It has been used for %d year.\n", carBrand, age)
  }

  override def greeting(): Unit = {
    printf("Welcome to my BYD car! %d\n", this.age) // 感觉相当于闭包, 把age给存起来了, 并不是属性
  }
}


object CarMain extends App {
  val bydCar = new BYDCar("yangkai", 14)
  bydCar.info()
  bydCar.greeting()
  println(bydCar.age)

  val bydCar2 = new BYDCar("yangkai2", 16)
  bydCar2.info()
  bydCar2.greeting()
  println(bydCar2.age)  // 仍然是10


  val bmwCar = new BMWCar("niubi")
  bmwCar.info()
  bmwCar.greeting()

}