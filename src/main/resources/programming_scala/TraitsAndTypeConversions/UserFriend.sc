trait Friend {
  val name:String
  def listen() = {println("Your friend " + name + " is listening")}
}

class Animal {
}


class Human(val name:String) extends Friend
class Man(override val name:String) extends Human(name)
class Woman(override val name:String) extends Human(name)
class Dog(val name:String) extends Animal with Friend {
  override def listen(): Unit = {println(name + " is listening quietly")}
}

val John = new Man("yangkai")
val sara = new Woman("limei")
val comet = new Dog("henry")

John.listen()
sara.listen()
comet.listen()

val manFriend:Friend = John
manFriend.listen()

def helpAsFriend(friend: Friend): Unit = {
  friend.listen()
}

helpAsFriend(John)
helpAsFriend(sara)
helpAsFriend(comet)
helpAsFriend(manFriend)

trait Flyable {
  def fly()
}

class Bird extends Flyable {
  override def fly(): Unit = println("I can fly")
}

val duck:Flyable = new Bird()
duck.fly()