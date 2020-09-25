package case_clz

case class Car(brand: String, price: Int)

object CarMain extends App {
  val myBMWCar = Car("BMW", 150)
  val myBYDCar = Car("BYD", 100)
  val myBenzCar = Car("BENZ", 1250)
  for (car <- List(myBenzCar, myBMWCar, myBYDCar)) {
    car match {
      case Car("BMW", 150) => println("hello, BMW")
      case Car("BYD", 100) => println("hello, BYD")
      case Car(brand, price) => println("Brand:" + brand + ", Price:" + price + ", do you want it?")
    }
  }
}
