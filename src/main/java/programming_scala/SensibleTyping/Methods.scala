
def printMethodInfo(methodName: String): Unit = {
  println("The return type of " + methodName + " is " +
  getClass.getDeclaredMethod(methodName, null).getReturnType.getName)
}

def method2() = {6}
def method3() = 6
def method4():Double = 6

/*
printMethodInfo("method2")
printMethodInfo("method3")
printMethodInfo("method4")
*/
def max(values: Int*):Int = values.foldLeft(values(0)){Math.max}

println(max(1,2,3,30,4,5))

val numbers = Array(1,1,2,3,30,4,5)
println(max(numbers:_*))