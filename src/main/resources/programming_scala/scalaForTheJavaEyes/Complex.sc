package programming_scala.scalaForTheJavaEyes

class Complex(val real: Int, val imaginary: Int) {
  def +(operand: Complex): Complex = {
    new Complex(real + operand.real, imaginary + operand.imaginary)
  }

  override def toString: String = {
    real + (if (imaginary < 0) "" else "+") + imaginary + "i"
  }
}

object Complex {
  def main(args: Array[String]): Unit = {
    val c1 = new Complex(5, -4)
    val c2 = new Complex(-33, 6)
    val sum = c1 + c2
    println("(" + c1 + ") + (" + c2 + ") = " + sum)
  }
}