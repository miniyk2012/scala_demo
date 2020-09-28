val array = Array(2, 3, 4, 5, 6, 7, 8)

println((0/:array)(_+_))
println((Int.MinValue/:array)(Math.max))
println(array.exists(_<0))

