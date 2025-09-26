sample(c(1, 2, 3, 4, 5, 6),
       5,
       replace=TRUE,
       prob=c(0.1, 0.1, 0.1, 0.1, 0.1, 0.5))

sample(c("H", "T"), 1)

#Q1
sample(c("H", "T"), 10, replace = TRUE)



FlipOnce <-function(){
  result <- sample(c("H ", "T"), 1)
  return(result)
}
FlipOnce()

FlipCoins <- function(n){ coins <- character(n) #initialize coins
for (i.coin in 1:n){ coins[i.coin] <- FlipOnce() #simulate one coin flip
}
return(coins) }

FlipCoins(20)

ProbHeads <- function(m.trials){
  coins <- FlipCoins(m.trials)
  k.Heads <- sum(coins == "H")
  return( k.Heads/ m.trials )
}

ProbHeads(100)


#Q2
FlipCoins <- function(n){
  return(sample( c("H","T"), n, replace = TRUE))
}
FlipCoins(100)

replicate(5,FlipCoins(10))

#Q3
MinAndMaxProbHeads <- function(m.trials, r.reps) {
  results <- replicate(r.reps, ProbHeads(m.trials))
  return(c(min(results), max(results)))
}

MinAndMaxProbHeads(10, 10)
MinAndMaxProbHeads(10, 100)
MinAndMaxProbHeads(10, 1000)
MinAndMaxProbHeads(10, 10000)
MinAndMaxProbHeads(100, 10)
MinAndMaxProbHeads(100, 100)
MinAndMaxProbHeads(100, 1000)
MinAndMaxProbHeads(100, 10000)
MinAndMaxProbHeads(1000, 10)
MinAndMaxProbHeads(1000, 100)
MinAndMaxProbHeads(1000, 1000)
MinAndMaxProbHeads(1000, 10000)
MinAndMaxProbHeads(10000, 10)
MinAndMaxProbHeads(10000, 100)
MinAndMaxProbHeads(10000, 1000)
MinAndMaxProbHeads(10000, 10000)