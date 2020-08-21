var more = 10
val addMore = (x:Int)=>x+more

addMore(1)
more = 30
addMore(1)


var sum = 0
val accumulator = (x:Int)=>sum+=x

accumulator(4)
sum
accumulator(6)
sum