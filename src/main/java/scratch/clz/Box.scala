package scratch.clz

import scala.collection.mutable

class Box[T] {
  val elems: mutable.Stack[T] = mutable.Stack()

  def remove(): Option[T] = {
    if (elems.isEmpty) None else Option(elems.pop)
  }

  def append(a: T): Unit = {
    elems.push(a)
  }
}


object BoxMain extends App {
  val box = new Box[Int]()
  box.append(10)
  box.append(20)
  println(box.remove())
  println(box.remove())
  println(box.remove())
}


