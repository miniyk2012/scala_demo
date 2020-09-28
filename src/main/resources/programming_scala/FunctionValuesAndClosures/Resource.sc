
class Resource private() {
  println("start transaction ...")

  private def cleanUp() = {
    println("clean up.")
  }

  def op1() = println("op1 ...")

  def op2() = println("op1 ...")

  def op3() = println("op1 ...")
}

object Resource {
  def use(operation: Resource => Unit): Unit = {
    val resource = new Resource()
    try {
      operation(resource)
    } finally {
      resource.cleanUp()
    }
  }
}

Resource.use(resource => {
  resource.op1()
  resource.op2()
  resource.op3()
})
