def powerOfTwo(x: Int): Int = {
  if (x == 0) 1 else 2 * powerOfTwo(x - 1)
}

powerOfTwo(10)

def sum(f: (Int) => Int, a: Int, b: Int): Int = {
  if (a > b) 0 else f(a) + sum(f, a + 1, b)
}

sum(x => x, 1, 100)

sum(x => x * 2, 1, 3)

sum(powerOfTwo, 9, 10)

