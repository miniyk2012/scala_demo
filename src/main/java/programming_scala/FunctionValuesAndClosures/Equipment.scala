class Equipment(val routine: Int => Int) {
  def simulate(input: Int) = {
    print("Running simulation...")
    routine(input)
  }
}

val calculate = {input:Int=>{println("equipment with " + input); input}}
val equipment = new Equipment(calculate)
equipment.simulate(10)
equipment.simulate(1)
