
class Vehicle {
  protected def checkEngine() {}
}

class Car extends Vehicle {
  def start() {
    checkEngine()
  }

  def tow(car: Car) {
    car.checkEngine()
  }

  // checkEngine in class Vehicle cannot be accessed in Vehicle
  // idea的提示功能不太好, 其实是没法编译的
  def tow(vehicle: Vehicle) {
    vehicle.checkEngine()
  }
}

class GasStation {
  //  method checkEngine in class Vehicle cannot be accessed in Vehicle
  def tow(vehicle: Vehicle) {
    vehicle.checkEngine()
  }
}
