import sys.process._
import java.net.URL
import java.io.File
import scala.language.postfixOps

object demo {
  /**
   * download file from url
   * @param url: file resource
   * @param filename: local file name
   * @return true will be return if download succeed
   */
  def fileDownloader(url: String, filename: String): Boolean = {
    try {
      new URL(url) #> new File(filename) !!
    } catch {
      case e: Exception => println(s"download file from $url failed. filename: $filename reason:", e.getMessage)
        return false
    }
    true
  }

  def main(args: Array[String]): Unit = {
    fileDownloader("https://git.qutoutiao.net/snippets/141/raw", "./output.xml")
  }
}
