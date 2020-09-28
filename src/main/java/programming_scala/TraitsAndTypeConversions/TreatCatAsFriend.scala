class Animal
trait Friend {
  val name:String
  def listen() = {println("Your friend " + name + " is listening")}
}

class Cat(val name:String) extends Animal


val lili = new Cat("lili") with Friend
val friend:Friend = lili
friend.listen()
def useFriend(friend: Friend) = {friend.listen()}
useFriend(lili)