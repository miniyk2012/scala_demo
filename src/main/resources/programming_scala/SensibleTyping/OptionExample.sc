def commentOnPractice(input: String) = {
  if (input == "test") Some("good") else None
}

for (input <- Seq("test", "prod")) {
  val comment = commentOnPractice(input)
  println("input " + input + " comment " + comment.getOrElse("Found no comments"))
}