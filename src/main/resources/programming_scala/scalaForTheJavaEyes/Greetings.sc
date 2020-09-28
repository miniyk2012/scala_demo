package programming_scala.scalaForTheJavaEyes

object Greetings extends App {
  for (i <- 1 to 3) {
    print(i + ",")
  }
  println("杨恺")

  for (i <- 1 until 3) {
    print(i + ",")
  }
  println("杨恺")
  (1 to 3).foreach(i => print(i + ","))
  println("杨恺")
}