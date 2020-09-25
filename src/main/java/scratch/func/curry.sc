def sum(a: Int, b: Int, c: Int) = a + b + c

val a = sum(1, _, _)

a(2, 3)

val b = sum _
val curriedSum = (sum _).curried


def multiplier(factor: Int)(x: Int) = x * factor
val byTwo = multiplier(2) _
byTwo(4)

def multiplier2(factor: Int, x: Int) = x * factor

val byThree = multiplier2(3, _)
byThree(3)


val curriedMultiplier = (multiplier2 _).curried
val x = curriedMultiplier(2)
x(10)
curriedMultiplier(3)(2)

