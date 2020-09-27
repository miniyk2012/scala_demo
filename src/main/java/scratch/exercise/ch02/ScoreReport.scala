package scratch.exercise.ch02

object ScoreReport {
  def main(args: Array[String]): Unit = {
    val inputFile = scala.io.Source.fromFile("src/main/resources/student.txt")
    val originData = inputFile.getLines().map(_.split("\\s+")).toList

    val courseNames = originData.head.drop(2)
    val allStudents = originData.tail
    val courseLength = courseNames.length

    def statistic(lines: List[Array[String]]) = {
      val s = for (i <- 2 to courseLength+1) yield {
        val temp = lines.map(_(i).toDouble)
         (temp.sum/lines.length, temp.min, temp.max)
      }
      s
    }

    val result = statistic(allStudents)
    println(result)

    val (females, males) = allStudents partition {_(1) == "female"}
    val female_result = statistic(females)
    val male_result = statistic(males)
    println(female_result)
    println(male_result)
  }
}
