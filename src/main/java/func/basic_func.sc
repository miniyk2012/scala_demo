val counter: (Int) => Int = { value => value + 1 }
counter(10)

val add = (a: Int, b: Int) => a + b
add(100, 20)

val show = (s: String) => println(s)
show("niubi")


val javaHome = () => System.getProperty("java.home")
println(javaHome())

val m1 = List(1, 2, 3)

val m2 = m1.map(_ * 2)

val m3 = m1.map(x => 2 * x)

val twice = (_: Int) * 2
val m4 = m1.map(twice)

def multiplier(factor: Int)(x: Int) = x * factor

multiplier(10)(4)

def multiplier2(factor: Int, x: Int) = x * factor
multiplier2(10, 4)


