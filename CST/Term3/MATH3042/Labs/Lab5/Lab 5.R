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
                       (Temp.deg.C - Monthly.Mean.Temp[Month]) / 
                         Monthly.SD.Temp[Month])
SFU.Weather$z.Temp.Anomaly <- z.Temp.Anomaly
hist(SFU.Weather$z.Temp.Anomaly)
histogram(~z.Temp.Anomaly, 
          data = SFU.Weather,
          col="#9DC3FA",
          type = "density",
          nint = 50, 
          xlim = c(-5,5),
          xlab = "Z score of Temperature (adjusted for Month)",
          main = "Standardized Temperature Anomaly",
          fit="normal")


SFU.Weather$Temp.Seg <- ifelse(SFU.Weather$z.Temp.Anomaly < -0.4307, "Cold", 
                              ifelse(SFU.Weather$z.Temp.Anomaly < 0.4307, "Mid",
                                     "Warm"
                                     )
                              )

#Q3
P.Cold <- sum(SFU.Weather$Temp.Seg == "Cold")/ nrow(SFU.Weather)
P.Mid <- sum(SFU.Weather$Temp.Seg == "Mid") / nrow(SFU.Weather)
P.Warm <- sum(SFU.Weather$Temp.Seg == "Warm") / nrow(SFU.Weather)

P.Cold; P.Mid; P.Warm
P.Cold + P.Mid + P.Warm

#Q4
SFU.Weather$Season <- with(SFU.Weather, dplyr::case_when(
  Month %in% c("Jun","Jul","Aug") ~ "Summer",
  Month %in% c("Sep","Oct","Nov") ~ "Autumn",
  Month %in% c("Dec","Jan","Feb") ~ "Winter",
  Month %in% c("Mar","Apr","May") ~ "Spring",
  TRUE ~ NA_character_
))
SFU.Weather$Season <- factor(SFU.Weather$Season,
                             levels = c("Summer","Autumn","Winter","Spring"))

Seasonal.Mean.Precip <- mosaic::mean(Total.Precip.mm ~ Season, data = SFU.Weather)

barplot(Seasonal.Mean.Precip[c("Summer","Autumn","Winter","Spring")],
        main = paste("Seasonal Mean Daily Precip. at SFU (n=", nrow(SFU.Weather), ")"),
        xlab = "Season",
        ylab = "Mean Daily Precipitation (mm)",
        ylim = c(0, 10))



SFU.Weather$Precipitation <- SFU.Weather$Total.Precip.mm > 0
SFU.Weather$Day.of.Week  <- weekdays(SFU.Weather$Date)

dow.tab <- table(SFU.Weather[c("Day.of.Week","Precipitation")])
dow.tab["Monday","TRUE"] / rowSums(dow.tab)["Monday"]

#Q5
p_any <- mean(SFU.Weather$Total.Precip.mm > 0, na.rm = TRUE)

w <- as.POSIXlt(SFU.Weather$Date)$wday
p_mon <- mean((SFU.Weather$Total.Precip.mm > 0)[w == 1], na.rm = TRUE)

independent <- abs(p_mon - p_any) < 0.01
data.frame(P_any = p_any, P_mon = p_mon,
           independent = abs(p_mon - p_any) < 0.01)

#Q6
SFU.Weather$Temp.Seg <- factor(SFU.Weather$Temp.Seg, levels = c("Cold","Mid","Warm"))
tab <- table(SFU.Weather$Temp.Seg, SFU.Weather$Total.Precip.mm > 0)

prop <- prop.table(tab, 1)[, "TRUE"]
names(prop) <- c("P(>0|Cold)", "P(>0|Mid)", "P(>0|Warm)")
prop

#Q7
P_warm   <- sum(tab["Warm",]) / sum(tab)
P_precip <- sum(tab[,"TRUE"]) / sum(tab)
P_precip_given_warm <- tab["Warm","TRUE"] / sum(tab["Warm",])
P_precip_given_warm * P_warm / P_precip

#Q8
tab <- with(SFU.Weather, table(Temp.Seg, Season, Total.Precip.mm > 0))
p <- prop.table(tab["Warm", , ], 1)[, "TRUE"]
round(p, 5)

#Q9
months <- c("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")
tab <- with(SFU.Weather, table(Month, Temp.Seg, Total.Precip.mm > 0))
p <- prop.table(tab[, "Warm", ], 1)[, "TRUE"]
p <- p[months]
best_month <- names(which.max(p))
best_value <- unname(max(p))
round(p, 5); best_month; best_value

barplot(p, xlab="Month", ylab="P(Precip>0 | Warm)", main="Effect of Warmth on Likelihood of Precip", ylim=c(0,1))


