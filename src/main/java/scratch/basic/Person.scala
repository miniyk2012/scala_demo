package scratch.basic

object Person {
  private var lastId = 0 // 因为是单例, 所有是共享的, 就和静态变量一样
  def newPersonId() = {
    lastId += 1
    lastId
  }
}


object PersonMain extends App {
  printf("the first id is %d\n", Person.newPersonId())
  printf("the second id is %d\n", Person.newPersonId())
}