val a = List(1, 2, 3)
a.foreach(println(_))

val university = Map("清华" -> "清华大学", "澳门" -> "澳门大学", "野鸡" -> "克莱登")
university.foreach(kv => println(kv._1 + ":" + kv._2))

university foreach { case (k, v) => println(k + ":" + v) }

for ((k, v) <- university) {
  println(k + ":" + v)
}

val books = List("Hadoop", "Spark")
books.map(s => s + "!")
books.map((s: String) => s.length)

books.flatMap(s => s.toList)

val func = (s: String) => List(s.length, 100)
books flatMap func

university filter { case (_, v) => v.contains("大学") }


val l = List(1, 2, 3, 4, 5, 6)
l filter {
  _ % 2 == 0
}

val t = List("Spark", "Hadoop", "Hbase")
(t find {
  _ startsWith "H"
}).getOrElse("Niubi")

t find {
  _ startsWith "Hp"
}

val list = List(1, 2, 3, 4, 5)
list map (_.toString) reduce ((x, y) => s"f($x, $y)")

val s1 = Set(1, 2, 3)
val s2 = util.Random.shuffle(s1)

s1 == s2

s1 reduce ((x, y) => x + y)
s2 reduce ((x, y) => x + y)

s1 reduce (_ - _)
s2 reduce (_ - _)

s1 reduceLeft (_ - _)
s2 reduceLeft (_ - _)

val list1 = List(1, 2, 3, 4, 5)

list1 map (_.toString) reduce ((x, y) => s"($x - $y)")
list1 map (_.toString) reduceLeft  ((x, y) => s"($x - $y)")
list1 map (_.toString) reduceRight   ((x, y) => s"($x - $y)")

(list1 map (_.toString) fold "10") ((x, y) => s"($x - $y)")
(list1 map (_.toString) foldLeft "10") ((x, y) => s"($x - $y)")
(list1 map (_.toString) foldRight "10") ((x, y) => s"($x - $y)")

val em = List.empty
em.fold(10)(_-_)

// 使用fold完成map的功能
(list1 foldRight List.empty[Int]) ((x, acc)=>x*2::acc)

val xs = List(1,2,3,4,5,6)
val sl = xs.sliding(3,2)  // 滑动step是2
while(sl.hasNext) {
  println(sl.next)
}


val x = Range(1,5)
x foreach println

for(a <- 0 to 10 if a%2==0) println(a)
