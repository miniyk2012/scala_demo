package clz

trait Flyable {
  var maxFlyHeight: Int

  def fly()
}

trait HasLegs {
  var legs: Int

  def move(): Unit = {
    printf("I can walk with %d legs\n", legs)
  }
}

class Animal(val category: String) {
  def info() {
    println("This is a " + category)
  }
}

class Bird(flyHeight: Int) extends Animal("Bird") with Flyable with HasLegs {
  var maxFlyHeight: Int = flyHeight

  def fly(): Unit = {
    printf("I can fly at the height of %d\n", maxFlyHeight)
  }

  var legs: Int = 2
}

object BirdMain extends App {
  val bird = new Bird(12)
  bird.info
  bird.fly
  bird.move
}