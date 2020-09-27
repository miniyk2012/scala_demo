import java.util

def inject(arr: Array[Int], initial: Int)(operation: (Int, Int)=>Int): Int = {
  var carryOver = initial
  arr.foreach(element => {
    carryOver = operation(carryOver, element)
  })
  carryOver
}

val array = Array(2, 3, 4, 5, 6, 7, 8)
println("sum of " + util.Arrays.toString(array) + " is " + inject(array, 0) { (x, y) => x + y})

