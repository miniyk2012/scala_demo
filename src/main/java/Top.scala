class Top(name: String, subname: String) {
  case class Nested(name:String)
  def show: Unit ={
    val c = new Nested(subname)
    printf("Top %s includes a Nested %s" , name, c.name)
  }
}


object TopMain extends App {
  val t = new Top("A","B")
  t.show
}


