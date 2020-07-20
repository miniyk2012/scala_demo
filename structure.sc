import util.control.Breaks._

val array = Array(1, 3, 10, 5, 4)

breakable {
  for (i <- array) {
    if (i > 5) break
    print(i)
  }
}
println()
for (i <- array) {
  breakable {
    if (i > 5) break
    print(i)
  }
}

val intValueArr = new Array[Int](3)
val myStrArr = Array("BigData", "Hadoop", "Spark")

intValueArr(1) = 100
intValueArr

val myMatrix = Array.ofDim[Int](3, 4)
val myCube = Array.ofDim[String](3, 2, 4)

myCube(0)(1)
val tuple: (String, Int, Double) = ("BigData", 2015, 45.0)
tuple._3

val (t1, t2, t3) = tuple
