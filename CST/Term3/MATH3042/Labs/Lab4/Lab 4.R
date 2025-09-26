#---
sample(c(1, 2, 3, 4, 5, 6),
       5,
       replace=TRUE,
       prob=c(0.1, 0.1, 0.1, 0.1, 0.1, 0.5))

sample(c("H", "T"), 1)

#Q1
sample(c("H", "T"), 10, replace = TRUE)


#---
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
sample(c("H", "T"), 10, replace=TRUE)

#Q2
FlipCoins <- function(n){
  return(sample( c("H","T"), n, replace = TRUE))
}
FlipCoins(100)

#---
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

#---
die.list <- c(3,6,3,1,2,6,4,2,1,5)
die.table <- table(die.list)
die.table
barplot(die.table)

#Q4
RollDice <- function(m.trials){
  rolls <- sample(1:6, m.trials, replace = TRUE)
  roll.table <- table(rolls)
  barplot(roll.table,
          main = paste("Outcome of", m.trials, "Die Rolls"),
          xlab = "Die Face",
          ylab = "Frequency")
}

RollDice(100)
RollDice(1000)
RollDice(10000)

#---
sample(1:6, 5, replace=TRUE)

#Q5
RollSomeDice <- function(m.trials, n.dice){
  X <- numeric(m.trials)
  
  for(i in 1:m.trials){
    rolls <- sample(1:6, n.dice, replace=TRUE)
    X[i] <- sum(rolls == 3)
  }
  
  X.table <- table(X)
  
  barplot(X.table,
          main = paste("Distribution of X (number of 3s out of", 
                       n.dice, 
                       "dice) Based on", 
                       m.trials, 
                       "Trials"),
          xlab = "Number of 3s (X)",
          ylab = "Frequency")
  
  return(X.table)
}

set.seed(69)
RollSomeDice(10000, 1)
RollSomeDice(10000, 2)
RollSomeDice(10000, 5)
RollSomeDice(10000, 10)
RollSomeDice(10000, 100)

#Q6
DrawCards <- function(m.trials, n.cards, replace){
  deck <- c(rep("R", 26), rep("B", 26))
  
  results <- replicate(
    m.trials,
    sum(sample(deck, n.cards, replace = replace) == "R")
  )
  
  hist(
    results,
    breaks = seq(-0.5, n.cards + 0.5, 1),
    main = paste("Distribution of Red Cards (n =", 
                 n.cards, 
                 ", m =", 
                 m.trials, 
                 ", replace =", 
                 replace, ")"),
    xlab = "Number of Red Cards")
}

DrawCards(10000, 1, TRUE)
DrawCards(10000, 5, TRUE)
DrawCards(10000, 10, TRUE)
DrawCards(10000, 20, TRUE)
DrawCards(10000, 50, TRUE)
DrawCards(10000, 1, FALSE)
DrawCards(10000, 5, FALSE)
DrawCards(10000, 10, FALSE)
DrawCards(10000, 20, FALSE)
DrawCards(10000, 50, FALSE)
