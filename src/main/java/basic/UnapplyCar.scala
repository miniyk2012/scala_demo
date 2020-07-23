package basic

class UnapplyCar(val brand: String, val price: Int) {
  def info(): Unit = {
    println("car brand is " + brand + " and price is " + price)
  }
}

object UnapplyCar {
  def apply(brand: String, price: Int) = {
    println("Apply called")
    new UnapplyCar(brand, price)
  }

  def unapply(arg: UnapplyCar): Option[(String, Int)] = {
    println("unapply called")
    Some((arg.brand, arg.price))
  }
}

object UnapplyMain extends App {
  var UnapplyCar(b, p) = UnapplyCar("MBW", 8000)
  println("brand is " + b + " and price is " + p)
}