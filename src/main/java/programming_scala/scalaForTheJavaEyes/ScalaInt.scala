package programming_scala.scalaForTheJavaEyes

import java.util

class ScalaInt {
  def playWith(): Unit = {
    val capacity : Int = 10
    val list = new util.ArrayList[String];
    list.ensureCapacity(capacity)
  }


}

object ScalaInt {
  def main(args: Array[String]): Unit = {
    new ScalaInt().playWith()
  }
}
