package clz

abstract class Element {
  type T
  var value:T
  def show:Unit
}

class IntElement(var value: Int) extends Element {
  type T = Int
  override def show: Unit = {
    printf("the value is %d\n", value)
  }
}

class CarElement(var value :Car) extends Element {
  type T = Car
  def show: Unit = {
    value.info()
  }
}


object ElementMain extends App {
  val intEle: Element = new IntElement(10)
  intEle.show

  val carEle: Element = new CarElement(new BYDCar("牛逼", 15))
  carEle.show
}
