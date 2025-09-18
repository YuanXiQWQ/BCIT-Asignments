# Env
library(MASS)
library(mosaic)
library(dplyr)

data("quine")
data("survey")

#Q1
days.fav <- favstats(~Days, data = quine)
days.fav$mean
days.fav$median
days.fav$sd

#Q2
#Sk = 3(bar X - Q2)/s
days.barX <- mean(~Days, data = quine)
days.Q2 <- median(~Days, data = quine)
days.s <- sd(~Days, data = quine)

days.Sk <- (3*(days.barX - days.Q2))/days.s
days.Sk

Sk.direction <- if(days.Sk > 0.5){
  "Skewed right"
}else if(days.Sk < -0.5){
  "Skewed left"
}else{
  "Symmetric"
}
Sk.direction

#Q3
range(quine$Days)
upper.limit <- seq(0, 85, by = 5)
histogram(
  ~Days,
  data = quine,
  breaks = upper.limit,
  xlab = "Days Absent",
  ylab = "Frequency",
  main = paste0(
            "Days Absent (n = ",
            sum(!is.na(quine$Days)),
            ")"
          )
)

#Q4
bwplot(
  ~Days,
  data = quine,
  xlab = "Days",
  main = paste0(
    "Days Absent (n = ",
    sum(!is.na(quine$Days)),
    ")"
  )
)

#Q5
paste0("The middle 50% of students were absent between ",
       days.fav$Q1,
       " and ",
       days.fav$Q3,
       " days."
      )

#Q6
#lf = Q1 - 1.5 * IQR
#uf = Q3 + 1.5 * IQR
days.IQR <- days.fav$Q3 - days.fav$Q1
days.lower.fence <- days.fav$Q1 - 1.5 * days.IQR
days.upper.fence <- days.fav$Q3 + 1.5 * days.IQR
sum(quine$Days < days.lower.fence | quine$Days > days.upper.fence)

#Q7
bwplot(Days ~ Age, data = quine,
       xlab = "Age Group",
       ylab = "Days of Absence",
       main = paste(
                "Days of Absence by Age (n =",
                sum(!is.na(quine$Days)),
                ")"
                )
       )

favstats(Days ~ Age, data = quine)

#Q8
favstats(Days ~ Age + Sex, data = quine)

#Q9
q.Lrn <- quantile(Days ~ Lrn, probs = seq(0.2, 0.8, 0.2) , data = quine)
q.Sex <- quantile(Days ~ Sex, probs = seq(0.2, 0.8, 0.2) , data = quine)
q.Eth <- quantile(Days ~ Eth, probs = seq(0.2, 0.8, 0.2) , data = quine)

q.Lrn
q.Sex
q.Eth

q.Lrn.diff <- range(abs(q.Lrn[1, -1] - q.Lrn[2, -1]))
q.Sex.diff <- range(abs(q.Sex[1, -1] - q.Sex[2, -1]))
q.Eth.diff <- range(abs(q.Eth[1, -1] - q.Eth[2, -1]))

q.Lrn.diff
q.Sex.diff
q.Eth.diff

#Q10
male.F0 <- filter(quine, Sex == "M" & Age == "F0")
cbind(round(100*percent_rank(male.F0$Days), digits = 2))

#Q11
z_scores <- scale(quine$Days)

pct_1sd <- mean(abs(z_scores) >= 1) * 100
pct_2sd <- mean(abs(z_scores) >= 2) * 100
pct_3sd <- mean(abs(z_scores) >= 3) * 100

pct_1sd
pct_2sd
pct_3sd