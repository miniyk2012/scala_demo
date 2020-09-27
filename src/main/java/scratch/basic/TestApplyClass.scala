package scratch.basic

class TestApplyClass {
  def apply(param: String): Unit = {
    printf("Apply method called: %s\n", param)
  }
}

object TestApplyClassMain extends App {
  val myObj = new TestApplyClass()
  myObj("afsd")
  myObj.apply("yangkai")
}