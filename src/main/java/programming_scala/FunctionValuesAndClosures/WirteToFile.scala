import java.io.{File, PrintWriter}

def writeToFile(fileName: String)(code: PrintWriter => Unit) = {
  val writer = new PrintWriter(new File(fileName))
  try {
    code(writer)
  } finally {
    writer.close()
  }
}
writeToFile("output.txt")(w=>w.write("hello world"))