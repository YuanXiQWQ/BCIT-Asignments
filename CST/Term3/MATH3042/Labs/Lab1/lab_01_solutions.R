# MATH 3042 - Lab 1 Solutions
# Author: Carl Gladish
# Date: Sept 1, 2024

library(mosaic)
library(dplyr)
library(stringi)

data(TenMileRace)

#Q1
slow.runners <- filter( TenMileRace, net > 8000 )
View(slow.runners)

#Q2
nrow(slow.runners)

#Q3a
female.runners <- filter( TenMileRace, sex == "F")

#Q3b
non.US.runners <- filter( TenMileRace,
                         nchar(as.character(state)) > 2)

#Q4
table(non.US.runners$state)

#Q5
table(droplevels(non.US.runners)$state)

#Q6
names(which.max( table(TenMileRace$state)))

#Q7 - number of male runners who finished before the fastest female
min.female.time <- min( female.runners$time )
fast.male.runners <- filter( TenMileRace, time < min.female.time )
nrow(fast.male.runners)

#Q8 - better rank(net) than rank(time)
better.rank.runners <- filter( TenMileRace, rank(net) < rank(time))
better.rank.runners[ which.min(better.rank.runners$time), 
                     c("age", "sex","state")]

#Q9 - typical runners
n.runners <- nrow( TenMileRace )
rank.5.percent <- floor(0.05*n.runners)
rank.95.percent <- ceiling(0.95*n.runners)
typical.runners <- filter( TenMileRace, rank(net) > rank.5.percent & 
                                      rank(net) < rank.95.percent )
head(typical.runners)

#Q10 - histogram of typical runners
hist(typical.runners$net, 
     xlab="Net Time (sec)", ylab="Frequency",
     main=paste("Typical Runners (n = ", 
                nrow(typical.runners), ")", sep=""))
