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
ones <- replicate(r.reps, 
                    sum(sample(1:6, n.dice, replace = TRUE) == 1)
                    )

freq <- table(factor(ones, levels = 0:n.dice))
rel  <- as.numeric(freq) / r.reps
result <- data.frame(x = 0:n.dice, relative_frequency = rel)
print(result, row.names = FALSE)

histogram(~ ones, 
        type="density",
        breaks=seq(-0.5, n.dice + 0.5, 1),
        xlab = "X",
        ylab = "P(X)",
        main = paste("Probability Histogram for X = Number of 1s in", 
          n.dice, "Dice Rolls"
          )
        )
}

RollDice(10, 100000)

#b
RollDiceBinom <- function(n.dice, r.reps) {
  ones <- rbinom(r.reps, n.dice, 1/6)
  freq <- table(factor(ones, levels = 0:n.dice))
  rel  <- as.numeric(freq) / r.reps
  result <- data.frame(x = 0:n.dice, relative_frequency = rel)
  print(result, row.names = FALSE)
  histogram(~ ones,
            type = "density",
            breaks = seq(-0.5, n.dice + 0.5, 1),
            xlab = "X", 
            ylab = "P(X)",
            main = paste("Probability Histogram for X = Number of 1s in",
                         n.dice, "Dice Rolls (rbinom)"))
}

RollDiceBinom(10, 100000)

#Q3
#a
mu <- 200 * 0.130
sigma <- sqrt(200 * 0.130 * (1 - 0.130))
c(mu = mu, sigma = sigma)

#b
Left.Sample.Prop <- function(n.students, p) {
  x <- sample(c("Left","Right"), n.students, replace=TRUE, prob=c(p, 1 - p))
  mean(x == "Left")
}
Left.Sample.Prop(200, 0.130)

#c
Dist.Left.Sample.Prop <- function(n.students, p, m.trials) {
  phats <- replicate(m.trials, Left.Sample.Prop(n.students, p))
  hist(phats, main="Distribution of p-hat", xlab="p-hat")
  print(mean(phats))
  print(sd(phats))
}
Dist.Left.Sample.Prop(200, 0.130, 10^5)

#Q4
DiceMeans <- function(n.dice, m.trials){
  xbars <- replicate(m.trials, mean(sample(1:6, n.dice, TRUE)))
  mu <- mean(xbars)
  sigma <- sd(xbars)
  freqs <- prop.table(table(xbars))
  barplot(freqs, 
          space=0, 
          xlab="xbar", 
          ylab="Relative Frequency",
          main=paste("Relative Frequency of xbar (n.dice=",
                     n.dice, ", m.trials=", m.trials, ")", sep=""))
  c(mu_xbar=mu, sigma_xbar=sigma)
}


DiceMeans(1, 10000)
DiceMeans(2, 10000)
DiceMeans(10, 10000)
DiceMeans(50, 10000)
DiceMeans(100, 10000)
