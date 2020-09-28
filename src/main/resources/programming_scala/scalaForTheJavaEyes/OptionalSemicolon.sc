import java.util

val list1 = new util.ArrayList[Int];
{
  println("Create list1")
}

val list2 = new util.ArrayList[Int] {
  println("Create list2")
}

println(list1.getClass)
println(list2.getClass)
println(list1.size())
println(list2.size())