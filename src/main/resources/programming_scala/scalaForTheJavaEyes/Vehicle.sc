
class Vehicle(val id: Int, var year: Int) {
  override def toString(): String = "ID: " + id + " Year: " + year

  def this(id: Int, year: Int, message: String) {
    this(id, year)
    println("build base: " + message)
  }
}

// extends Vehicle(id, year)相当于调用了基类的构造函数
class Car(override val id: Int, year: Int,
          var fuelLevel1: Int) extends Vehicle(id, year, "hello") {
  override def toString(): String = super.toString() + " Fuel Level: " + fuelLevel1

  def this(id: Int, year: Int) {
    this(id, year, -10)
  }
}

val car = new Car(1, 2009, 100)
car.fuelLevel1 = 19
println(car)
car.year = 100
println(car)
println(car.year)
val car2 = new Car(10, 20)
println(car2)