abstract class Check {
  def check():String = "Check Application Details ..."
}

trait CreditCheck extends Check {
  override def check(): String = "Check Credit ..." + super.check()
}

trait CriminalCheck extends Check {
  override def check(): String = "Check Criminal ..." + super.check()
}

trait EmploymentCheck extends Check {
  override def check(): String = "Check Employment ..." + super.check()
}

class OneCheck extends Check {
  override def check():String = "One Check..."
}
val apartmentApp = new Check with CreditCheck with CriminalCheck
println(apartmentApp.check())

val oneCheck = new OneCheck() with  EmploymentCheck with CriminalCheck
println(oneCheck.check())