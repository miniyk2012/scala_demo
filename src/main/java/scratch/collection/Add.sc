
def add = (x: Int, y: Int) => x + y

add(5, 6)
add.apply(30, 45)


def error(message: String): Nothing = {
  throw new RuntimeException(message)
}
def divide(x: Int, y: Int): Int = {
  if (y != 0)
    x / y
  else
    error("cant't divide by zero")
}

//divide(4, 0)

case class Book(name: String)

val books = Map("hadoop" -> Book("Hadoop"),
  "spark" -> Book("Spark"),
  "hbase" -> Book("Hbase"),
)

books.get("spark")
books.get("yangkai")
books.get("spark").get
books.get("yangkai").getOrElse(Book("Unknown"))

