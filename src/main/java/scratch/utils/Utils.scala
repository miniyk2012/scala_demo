package scratch.utils

;

import java.io.File

object Utils {
  def removeFile(path: String): Unit = {
    val dir = new File(path)
    removeDir(dir)
    dir.delete()
  }

  def removeDir(dir: File): Unit = {
    val files = dir.listFiles()
    if (files == null) {
      return
    }

    for (file <- files) {
      if (file.isDirectory) removeDir(file)
      else System.out.println(file + ":" + file.delete)
    }
  }
}
