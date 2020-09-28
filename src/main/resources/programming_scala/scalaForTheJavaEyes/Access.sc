

class Microwave {
  def start() = println("started")

  def stop() = println("stopped")

  private def turnTable() = println("turning table")
}

val microwave = new Microwave
microwave.start
