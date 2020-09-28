def totalResultOverRange(number: Int, codeBlock: Int => Int): Int = {
  var result = 0
  for (i <- 1 to number) {
    result += codeBlock(i)
  }
  result
}

println(totalResultOverRange(10, i => i))
println(totalResultOverRange(10, i => if (i % 2 == 0) i else 0))
println(totalResultOverRange(10, i => if (i % 2 == 1) i else 0))

def inject(arr: Array[Int], initial: Int, operation: (Int, Int) => Int): Int = {
  var carryOver = initial
  arr.foreach(element => {
    carryOver = operation(carryOver, element)
  })
  carryOver
}

val array = Array(2, 3, 4, 5, 6, 7, 8)
println("sum of " + array.toString + " is " + inject(array, 0, (x, y) => x + y)
)
println("max of " + array.toString + " is " + inject(array, Int.MinValue, (x, y) => Math.max(x, y))
)

val sumValue = (0 /: array) { (sum, elem) => sum + elem }  // curry化
val maximun = (Int.MinValue /: array) { (larger, elem) => Math.max(larger, elem) }
println("sum of " + array.toString + " is " + sumValue)
println("max of " + array.toString + " is " + maximun)

