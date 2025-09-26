# MATH 3042 - Lab 3 Solutions
# Author: Carl Gladish
# Modified: Sept 23, 2025

library(MASS)
library(mosaic)
library(dplyr)

data(quine)

#1.
res <- favstats(~Days, data=quine)
X.bar <- res["mean"]
Q2 <- res["median"]
s <- res["sd"]

#2.
Sk <- 3*(mean(~Days, data=quine) - median(~Days, data=quine)) /
           sd(~Days, data=quine)
# Since Sk > 1, this variable is positively skewed
# i.e., skewed right

#3.
n <- sum(!is.na(quine$Days))
histogram(~Days, 
          data=quine,
          breaks=seq(0,85,5),
          right=FALSE,
          xlab="Days Absent", 
          main=paste0("Days Absent for Australian Students (n = ",
                     n,")"))

# the histogram has a long right tail, which is reflective of
# the fact that Sk > 1

#4.
bwplot(~Days, data=quine, xlab="Days Absent",
       main=paste("Days Absent for Australian Students ( n =",n,")"))

#5.
quantile(~Days, data=quine, probs=c(0.25, 0.75))
# The middle 50% of students were absent between 5.0 
# and 22.75 days.

#6. 
# to count outliers, use the formula for the lower and upper fences
attach(quine)
n.outliers <- 
  sum( (Days < (quantile(~Days, probs=0.25)-IQR(~Days)*1.5)) |
       (Days > (quantile(~Days, probs=0.75)+IQR(~Days)*1.5)))
detach()

paste("There are",n.outliers, "outliers.")

#7. 
bwplot(Days~Age, data=quine, 
       xlab="Age Group", ylab="Days Absent",
       main="Comparison of Student Absences by Age Group")
favstats(Days~Age, data=quine)
# Observations:
#   All groups have at least one student with perfect attendance.  
#   The two older age groups tend to have greater days absent, based on 
#     their mean and median values.
#   Group F2 has the greatest variability in days absent, based on standard
#     deviation.

#8.
sd(Days~Age+Sex, data=quine)
which.min(sd(Days~Age+Sex, data=quine))
which.max(sd(Days~Age+Sex, data=quine))
#   Male students in age group F1 have the lowest variability (most consistant).
#   Female students in age group F2 have the greatest variability (least consistant).

#9.
quantile(Days~Lrn, probs=seq(0.2, 0.8, by=0.2), data=quine)
quantile(Days~Sex, probs=seq(0.2, 0.8, by=0.2), data=quine)
quantile(Days~Eth, probs=seq(0.2, 0.8, by=0.2), data=quine)

# Ethnicity (aboriginal or not) makes the greatest difference. 

#10. 
cbind(round(100*percent_rank( quine$Day[ quine$Sex=="M" & 
                                 quine$Age=="F0" ] )))

#11.
options(digits=7)
n <- nrow(quine)
n1 <- sum( abs(scale( quine$Days )) >= 1 )
n2 <- sum( abs(scale( quine$Days )) >= 2 )
n3 <- sum( abs(scale( quine$Days )) >= 3 )
100*c(n1, n2, n3)/n

# 32 students (21.9% of students) were at least 1 std dev from the mean
# 8 students (5.5% of students) were at least 2 std dev from the mean
# 3 students (2.1% of students) were at least 3 std dev from the mean

#12.
#According to Chebyshev's Rule, we would have
#   At least 0% of students are within 1 std dev from the mean, which implies
#   that at most 100% are at least 1 std dev away from the mean.

#   At least 75% of students are within 2 std dev from the mean, which implies
#   that at most 25% are at least 2 std dev from the mean.

#   At least 88.9% of students are within 3 std dev from the mean, which implies
#   that at most 11.1% are at least 3 std dev from the mean.

# All three of these statements are consistent with the percentages found
# above ( 21.9% <= 100%,  5.5% <= 25%,  2.1% <= 11.1% )

#According to the Empirical Rule, for a normally distributed variable:
#  100% - 68% = 32% of individuals are at least 1 std dev from the mean
#  100% - 95% = 5% of individuals are at least 2 std dev from the mean
#  100% - 99.7% = 0.3% of individuals are at least 3 std dev from the mean

# This does not agree with the percentages we found. The reason is that the
# Days variable is not a normally distributed variable. (It is not symmetrical.)

