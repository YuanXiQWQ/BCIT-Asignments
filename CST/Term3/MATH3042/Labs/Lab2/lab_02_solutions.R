# MATH 3042 - Lab 2 Solutions
# Author: Carl Gladish
# Modified: Sept 19, 2025

library(MASS)
library(aplpack)
library(mosaic)


#Q1. 
percents <- c(10, 30, 5, 35, 20)
home.type <- c("On Campus", "Parents", "Alone",
               "Roommates", "Spouse")

pie(percents, home.type, 
    main="Living Arrangments of Students",
    col=c("pink", "snow","turquoise", "orange", "skyblue"))


#Q2
data(survey)

freq.tab <- table(survey$W.Hnd)
new.labels <- paste( names(freq.tab), "\n(", 
                     freq.tab, " students)", sep="")

pie(freq.tab, new.labels, col=c("grey", "pink"),
    radius=1.0,
    main=paste("Writing Hand of Students (n =",
               sum(freq.tab),
               ")", sep=""))

#Q3
barplot(freq.tab,horiz=TRUE, 
        main=paste0("Writing hands of Students (n =", sum(freq.tab),")"))
  
#Q4
stem(survey$Height,scale=2)

#Q5
mens.Heights <- filter(survey, Sex=="Male")$Height
womens.Heights <- filter(survey, Sex=="Female")$Height
stem.leaf.backback(mens.Heights, womens.Heights, m=4, depths=FALSE)

#Q6
# Mens' heights tend to be about 20cm taller than womens' heights,
# based on the locations of the peaks in the stem plot.

#Q7
male.Age <- survey[ survey$Sex=="Male","Age"]
male.Age <- male.Age[!is.na(male.Age)]
female.Age <- survey[ survey$Sex=="Female","Age"]
female.Age <- female.Age[!is.na(female.Age)]

overall.min <- min(c(range(male.Age),range(female.Age)))
overall.max <- max(c(range(male.Age),range(female.Age)))

#use bins of width 5 starting from 15 and going to 75
male.Ages <- cbind(table(cut(male.Age,
                             breaks=seq(15, 75, by=5),
                             right=FALSE)))

female.Ages <- cbind(table(cut(female.Age,
                             breaks=seq(15, 75, by=5),
                             right=FALSE)))
male.Ages
female.Ages

barplot(t(male.Ages),main="Male Age distribution")
barplot(t(female.Ages),main="Female Age distribution")

# The distributions of Male and Females ages is quite similar. 

#Q8
numerical.Heights <- survey[!is.na(survey$Height),"Height"]
hist(numerical.Heights,
     breaks=seq(150, 200, by=2.5),
     col="pink",
     xlab="Heights (cm)", ylab="Frequency",
     main=paste("Heights of", length(numerical.Heights),"students"))


#Q9
all.Heights <- survey[ !is.na(survey$Height), "Height"]
upper.class.limits <- seq(145,200,5)
Height.freq <- table(cut(all.Heights, breaks=upper.class.limits,right=TRUE))
Height.cumul.freq <- cumsum(Height.freq)
Height.cumul.freq <- c(0, Height.cumul.freq)

plot(upper.class.limits, Height.cumul.freq,
     col="black",type="b", pch=1,
     xlab="student heights (cm)",ylab="number of students",
     main=paste("Ogive of", length(all.Heights),"student heights"))

#Q10
library(mosaic)
xyplot(survey$Wr.Hnd ~ survey$Height,
       xlab="Span of Writing Hand (cm)",
       ylab="Height of Student (cm)",
       main=paste0("Height vs Hand Span (n = ", nrow(survey), ")"))

#Q11
xyplot(survey$Wr.Hnd ~ survey$NW.Hnd,
       xlab="Span of Non-Writing Hand (cm)",
       ylab="Span of Writing Hand (cm)",
       main=paste("Hand Spans of", nrow(survey), "students"))

xyplot(survey$Height ~ survey$Pulse,
       xlab="Student Height (cm)",
       ylab="Pulse Rate (beats/min)",
       main=paste("Heights and Pulse Rates of", nrow(survey), "students"))

# There is evidently a much strong correlation between Wr.Hnd and NW.Hnd 
# measurements than between Height and Pulse for these students.
# This is because students' hands tend to be about the same size as each
# other, while their Pulse rates do not vary in a consistant way with 
# their Height.
