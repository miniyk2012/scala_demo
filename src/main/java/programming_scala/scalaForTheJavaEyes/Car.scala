// 主构造函数
class Car(val year: Int) {
  private var milesDriven: Int = 0
  def miles() = milesDriven
  def drive(distance: Int): Unit = {
    milesDriven += distance
  }
}

val car = new Car(2009)
println(car.year)
println(car.miles)
car.drive(100)
car.drive(200)
println(car.miles)

