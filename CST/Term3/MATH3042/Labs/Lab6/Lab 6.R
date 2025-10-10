library(dplyr)
library(mosaic)

#Q1
#n=300, p=1/3
#a
Exactly.100 <- dbinom(100, 300, 1/3)
#b
At.Most.100 <- pbinom(100,300,1/3)
#c
Fewer.Than.100 <- pbinom(99,300,1/3)
#d
At.Least.110 <- 1 - pbinom(109,300,1/3)
#e
Less.Than.90 <- pbinom(89,300,1/3)
More.Than.110 <- 1 - pbinom(110,300,1/3)
Between.90.110 <- 1 - Less.Than.90 - More.Than.110
round(c(
  Exactly.100   = Exactly.100,
  At.Most.100   = At.Most.100,
  Fewer.Than.100 = Fewer.Than.100,
  At.Least.110  = At.Least.110,
  Between.90.110 = Between.90.110
), 4)

#Q2
#a
RollDice <- function(n.dice, r.reps) {
results <- replicate(r.reps, 
                    sum(sample(1:6, n.dice, replace = TRUE) == 1)
                    )

barplot(proportions(table(results)), 
        breaks=seq(-0.5,n.dice+0.5,1),
        xlab = "Number of 1s",
        ylab = "Relative Frequency",
        main = paste(
          "Probability Histogram for", n.dice, "Dice Rolls"
          )
        )
}

RollDice(10, 100000)



RollDice <- function(n.dice, r.reps) {
  x.vals <- numeric(r.reps)
  for(i in 1:r.reps){
    dice <- sample(1:6, n.dice, replace = TRUE)
    x.vals[i]
  }
}
