# Lab 5 Solutions
# Author: Carl Gladish
# Modified: Oct 1, 2025

library(dplyr)
library(mosaic)

setwd(Sys.getenv("MATH_3042_ROOT"))
data.file <- file.path("Data","SFU.Weather.with.Precip.csv")

SFU.Weather <- read.csv(data.file,
                        colClasses = c("Date", "numeric", "character",
                                       "numeric", "numeric", "numeric", "numeric"))

#remove days with NA values in Temp or Precip
SFU.Weather <- filter( SFU.Weather, !is.na(Temp.deg.C), !is.na(Total.Precip.mm))

n.obs <- nrow(SFU.Weather)

#define calendar sequence of months
months <- c("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

#Question1 
#Calculate mean and sd grouped by Month
Monthly.Mean.Temp <- mean(Temp.deg.C ~ Month, data=SFU.Weather)
Monthly.SD.Temp   <-   sd(Temp.deg.C ~ Month, data=SFU.Weather)
barplot(Monthly.Mean.Temp[months],
        col="blue", density=20,
        xlab="Month", ylab="Mean Daily Temp (deg C)",
        ylim=c(0, 20),
        main=paste0("Monthly Mean Daily Temperatures at SFU (n=", n.obs,")"))
#Observations:
#   Jan and Dec are the coldest months.
#   July and August are the warmest months.


SFU.Weather$Z.Temp.Anomaly <-
  with(SFU.Weather,
       (Temp.deg.C - Monthly.Mean.Temp[Month]) /
         Monthly.SD.Temp[Month])

#Question 2 - Histogram of Z values for Temperature Anomalies
histogram(~Z.Temp.Anomaly, data=SFU.Weather,
          breaks=seq(-7, 7, 0.2),
          fit="normal",
          type="density",
          xlim=c(-5, 5),
          main="Standardized Temperature Anomaly",
          xlab="Z score of Temperature (adjusted for Month)",
          ylab="Probability Density")

#Segment the data frame according to Z.Temp.Anomaly
SFU.Weather$Temp.Seg <- ifelse( SFU.Weather$Z.Temp.Anomaly < -0.4307,
                                "Cold",
                                ifelse( SFU.Weather$Z.Temp.Anomaly <= 0.4307,
                                        "Mid",
                                        "Warm"))

#Question 3 - Probability of each temperature segment
Temp.Seg.tab <- proportions(table(SFU.Weather$Temp.Seg))
P.Cold <- Temp.Seg.tab[["Cold"]]
P.Mid <-  Temp.Seg.tab[["Mid"]]
P.Warm <- Temp.Seg.tab[["Warm"]]

#Question 4 - segment according to seasons
seasons <- c(Dec="Winter", Jan="Winter", Feb="Winter",
             Mar="Spring", Apr="Spring", May="Spring",
             Jun="Summer", Jul="Summer", Aug="Summer",
             Sep="Autumn", Oct="Autumn", Nov="Autumn")
SFU.Weather$Season <- seasons[SFU.Weather$Month]

Seasonal.Mean.Precip <- mean(Total.Precip.mm ~ Season,
                             data=SFU.Weather, na.rm=TRUE)

barplot(Seasonal.Mean.Precip[c("Summer", "Autumn", "Winter", "Spring")],
        col="green",density=20,
        xlab="Month", ylab="Mean Daily Precipitation (mm)",
        ylim=c(0, 10),
        main=paste0("Seasonal Mean Daily Precip. at SFU (n=", n.obs,")"))


# Define Boolean variable Precipitation to check if any precipitation occurred
SFU.Weather$Precipitation <- (SFU.Weather$Total.Precip.mm > 0)

# Add categorical variable for days of the week (Monday, Tuesday, ...)
SFU.Weather$Day.of.Week <- weekdays( SFU.Weather$Date)

# Are Mondays more likely to have Precipitation?
dow.tab <- table(SFU.Weather[c("Day.of.Week","Precipitation")])
dow.tab["Monday","TRUE"] / rowSums(dow.tab)["Monday"]  

#Question 5 - What is the probability of Precipitation?
P.Precip <- proportions(table(SFU.Weather$Precipitation))[["TRUE"]]
P.Precip
#Precipitation occurred on 1222 / (1284+1222) = 48.76% of Mondays
#Precipitation occurred on 48.78% of all Days
#These probabilities are practically equal, so Precipitation on a given day
# is independent of the event that that day is Monday.

#Question 6 - Precipitation according to temperature segment
T.Seg.Precip.tab <- table( SFU.Weather[c("Temp.Seg","Precipitation")])

P.Precip.given.Cold <- T.Seg.Precip.tab["Cold","TRUE"] / 
                        rowSums(T.Seg.Precip.tab)[["Cold"]]

P.Precip.given.Mid <- T.Seg.Precip.tab["Mid","TRUE"] / 
                        rowSums(T.Seg.Precip.tab)[["Mid"]]

P.Precip.given.Warm <- T.Seg.Precip.tab["Warm","TRUE"] /
                        rowSums(T.Seg.Precip.tab)[["Warm"]]

# According to these three probabilities, on Cold days there is
# the highest probability of Precipitation. Yes, the hypothesis 
# supported by the data.


#Question 7 - Bayes' Theorem
P.Warm.given.Precip <- 
        P.Warm * P.Precip.given.Warm / 
         (P.Cold * P.Precip.given.Cold + P.Mid * P.Precip.given.Mid +
            P.Warm * P.Precip.given.Warm)
P.Warm.given.Precip
#equivalently:
0.3173*0.3971 / (0.3439*0.5715 + 0.3388*0.4879 + 0.3173*0.3971)

#to confirm, you can calculate P(Warm | Precip>0) directly from the data
with(SFU.Weather,
     sum( Temp.Seg=="Warm" & Precipitation)/
       sum( Precipitation))


#Question 8 - Probability of Precipitation on Warm days for each Season
Precip.by.Season <- table(SFU.Weather[c("Season",
                                        "Temp.Seg",
                                        "Precipitation")])
Precip.by.Season
Precip.by.Season["Summer","Warm","TRUE"]/sum(Precip.by.Season["Summer","Warm",])
Precip.by.Season["Autumn","Warm","TRUE"]/sum(Precip.by.Season["Autumn","Warm",])
Precip.by.Season["Winter","Warm","TRUE"]/sum(Precip.by.Season["Winter","Warm",])
Precip.by.Season["Winter","Cold","TRUE"]/sum(Precip.by.Season["Winter","Cold",])
sum(Precip.by.Season["Winter",,"TRUE"])/sum(Precip.by.Season["Winter",,])
Precip.by.Season["Spring","Warm","TRUE"]/sum(Precip.by.Season["Spring","Warm",])

#Findings: Precipitation is more likely in general on cold days, However, we
#          found that the seasons affect this relationship.
#          In the Winter, precipitation is more likely (72%) on Warm days;
#          in the Summer, precipitation is far less likely (9%) on Warm days.

#Question 9 - Influence of Warmth on Precip, month by month
Precip.by.Month <- table(SFU.Weather[c("Month", "Temp.Seg", "Precipitation")])

P.Precip.given.Warm.by.Month <- Precip.by.Month[,"Warm","TRUE"] / 
  rowSums(Precip.by.Month[,"Warm",])

which.max(P.Precip.given.Warm.by.Month)
# In December, the probability of Precipitation on Warm days is the highest.

barplot(P.Precip.given.Warm.by.Month[months],
        xlab="Month",
        ylab="P(Precip > 0 | Warm)",
        col="pink",
        ylim=c(0, 0.8),
        main="Effect of Warmth on Likelihood of Precip ")

