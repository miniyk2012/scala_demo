
class Pet(val name: String) {
  override def toString: String = name
}

class Dog(override val name: String) extends Pet(name)

def workWitPets[T <: Pet](pets: Array[T]): Unit = {
  println(pets.mkString(", "))
}

def copyPets[S, D >: S](fromPets: Array[S], toPets: Array[D]) = {

}
val dogs = Array(new Dog("杨恺"), new Dog("lala"))

workWitPets(dogs)
val pets = new Array[Pet](10)
copyPets(pets, dogs)

var list1 = new Array[Int](3)
var list2: Array[Any] = list1