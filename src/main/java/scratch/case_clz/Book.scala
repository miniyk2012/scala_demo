package case_clz

case class Book(name: String, price: Double) {
  var name1: String = "100a"
}


object BookTest extends App {
  val books = Map("hadoop" -> Book("Hadoop", 5),
    "spark" -> Book("Spark", 10), "hbase" -> Book("hbase", 100))
  println(books.get("spark").getOrElse(Book("", 0)).name1)
  println(books.get("sparks").getOrElse("yangkai"))

}
