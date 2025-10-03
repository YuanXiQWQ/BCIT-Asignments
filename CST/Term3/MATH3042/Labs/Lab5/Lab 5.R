library(dplyr)
library(mosaic)
setwd("F:/JerryCode/BCIT-Assignments/CST/Term3/MATH3042/Labs/Lab5")
data.file <- "SFU.Weather.with.Precip.csv"
SFU.Weather <- read.csv(data.file, 
                        colClasses = c("Date", "numeric", 
                                       "character", "numeric", 
                                       "numeric", "numeric", 
                                       "numeric"))
#Q1
dow <- weekdays(SFU.Weather$Date)
SFU.Weather$dow <- dow
SFU.Weather <- filter(SFU.Weather, !is.na(Temp.deg.C), !is.na(Total.Precip.mm))
Monthly.Mean.Temp <- mean(Temp.deg.C ~ Month, data=SFU.Weather)
Monthly.SD.Temp <- sd(Temp.deg.C ~ Month, data=SFU.Weather)
barplot(Monthly.Mean.Temp[c("Jan", "Feb", "Mar", "Apr", 
                            "May", "Jun", "Jul", "Aug", 
                            "Sep", "Oct", "Nov", "Dec")],
        main = paste("Mean Daily Temperature by Month at SFU (n=",
                     nrow(SFU.Weather),
                     ")"),
        xlab = "Month",
        ylab = "Mean Daily Temp (deg C)")

#Q2
z.Temp.Anomaly <- with(SFU.Weather,
                       (Temp.deg.C - Monthly.Mean.Temp[Month])/
                         Monthly.SD.Temp[Month])
SFU.Weather$z.Temp.Anomaly <- z.Temp.Anomaly
hist(SFU.Weather$z.Temp.Anomaly)
histogram(~z.Temp.Anomaly, 
          data = SFU.Weather,
          col="#9DC3FA",
          breaks = 50, 
          xlim = c(-5,5),
          fit="normal")


SFU.Weather$Tem.Seg <- ifelse(SFU.Weather$z.Temp.Anomaly < -0.4307, "Cold", 
                              ifelse(SFU.Weather$z.Temp.Anomaly <= 0.4307, "Mid",
                                     "Warm"
                                     )
                              )

#Q3
P.Cold <- sum(SFU.Weather$Temp.Seg == "Cold")/ nrow(SFU.Weather)
P.Mid <- sum(SFU.Weather$Temp.Seg == "Mid") / nrow(SFU.Weather)
P.Warm <- sum(SFU.Weather$Temp.Seg == "Warm") / nrow(SFU.Weather)

#Q4
SFU.Weather$Precipitation <- (SFU.Weather$Total.Precip.mm >0)
SFU.Weather$Day.of.Week <- weekdays(SFU.Weather$Date)