import scala.collection.mutable
import scala.collection.immutable
//import scala.scratch.collection.immutable._

Nil
var strList = List("bigdata", "hadoop", "spark")
var otherList = "apache" :: strList
strList.head
strList.tail

val vec1 = Vector(1, 2)
val vec2 = 3 +: 4 +: vec1
vec1.+:(4).+:(3)
val vec3 = vec2 :+ 5 :+ 6

val a = 1 to 10
val b = 1 to 10
val c = 1 until 10
val d = 0 to 9 by 2
val e = 0 until 10 by 2
b.toList
c.toList
d.toList
e.toList
val g = 'a' to 'e'
g(0)
g(1)

val g2 = BigDecimal(9.474) to BigDecimal(49.474) by BigDecimal(1.0)


val mutableL1: mutable.ListBuffer[Int] = mutable.ListBuffer(10,20,30)

mutableL1 += 40
val mutableL2 = mutableL1 :+ 50
mutableL1.insert(2, 70)
mutableL1
mutableL1 -= 40
mutableL2

var mySet1 = Set("abc", "def")  // 不可变集
mySet1 += "niubi"  // 生成了一新的Set

val myMutableSet = mutable.Set("Database")  // 可变集
myMutableSet += "Cloud Computing"
myMutableSet.add("Cloud Computing1")

var university1 = Map("XMU" -> "厦门大学", "THU" -> "清华大学")

university1 += ("XMU" -> "lihai")  // 产生一个新的不可变集
university1("XMU")


var university2 = mutable.Map("XMU" -> "厦门大学", "THU" -> "清华大学")
university2("XMU") = "xiamen"
university2("FZU") = "福州大学"
university2 += ("XMU" -> "下面")
university2
university2.values
university2.keys
for (i <- university2.keys) {
  println(university2(i))
}

println()
for (x <- myMutableSet) {
  println(x)
}
val iter = Iterator(1,2,3)
while (iter.hasNext) {
  println(iter.next())
}









