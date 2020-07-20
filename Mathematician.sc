def square(x: Int) = x * x

square(2)
square(14)

for (i <- 1 to 3) println(i)

import java.io.FileReader
import java.io.FileNotFoundException

try {
  val f = new FileReader("input.txt")
} catch {
  case ex: FileNotFoundException => println("Error: "+ex)
}

import java.io.PrintWriter
val outputFile = new PrintWriter("test.txt")
val i = 9
outputFile.print("%3d --> %d\n".format(i,i*i))
outputFile.println(f"$i%3d --> ${i*i}%d")
outputFile.close()