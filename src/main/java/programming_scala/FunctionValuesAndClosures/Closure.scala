def workThrough(number: Int)(closure: Int => Unit): Unit = {
  for (value <- 1 to number) {
    closure(value)
  }
}

var result = 0
def sum(value: Int) = {
  result += value
}
workThrough(5)(sum)
println(result)

result = 0
workThrough(10)(sum)
println(result)

var product = 1
workThrough(5) {product *= _}
println(product)

