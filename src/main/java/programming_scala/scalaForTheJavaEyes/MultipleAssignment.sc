def getPersonalInfo(key: Int) = {
  ("yangkai", "mail", 45)
}


val (name, mail, age) = getPersonalInfo(10)
println("name is " + name)
println("mail is " + mail)
println("age is " + age)


val info = getPersonalInfo(10)
println("name is " + info._1)
