def square(x: Int) = x * x

square(2)
square(14)

for (i <- 1 to 3) println(i)

import java.io.FileReader
import java.io.FileNotFoundException
import java.nio.file.FileSystems

try {
  val f = new FileReader("input.txt")
} catch {
  case ex: FileNotFoundException => println("Error: "+ex)
}

import java.io.PrintWriter
val outputFile = new PrintWriter("tests.txt")
val i = 9
outputFile.print("%3d --> %d\n".format(i,i*i))
outputFile.println(f"$i%3d --> ${i*i}%d")
outputFile.close();

print(System.getProperty("user.dir"))


import java.nio.file.Paths

val currentRelativePath = Paths.get("")
val s = currentRelativePath.toAbsolutePath.toString
System.out.println("Current relative path is: " + s)

var path = new StringBuffer();
path.append("a").append("niubi")