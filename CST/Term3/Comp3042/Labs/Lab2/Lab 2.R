# 1
percents <- c(10,30,5,35,20);
home.type <- c("On Campus","Parents","Alone", "Roommates", "Spouse")
pie(percents,
    labels =  home.type,
    main = "Living Arrangements of Students",
    col = c("#f5c1ca","#fefafa","#73dccf","#f2a83d","#95cbe7")
    );

# 2
library(MASS);
View(survey);
head(survey);

levels(survey$W.Hnd);
freq.tab <- table(survey$W.Hnd)
new.labels <- paste(names(freq.tab), "\n(", freq.tab, " students)", sep="");

pie(freq.tab, 
    labels = new.labels,
    main = paste0("Writing Hand of Students (n = ", sum(freq.tab), ")"),
    col = c("#bdbdbd","#f5c1ca")
    );

# 3
barplot(freq.tab,
        horiz = TRUE,
        names.arg = names(freq.tab),
        main = paste0("Writing Hand of Students (n = ", sum(freq.tab), ")"),
        xlab = "Count",
        col  = "#bdbdbd"
        );

# 4
stem(survey$Height, scale = 2);

# 5
install.packages("aplpack");
library(aplpack);
mens.heights   <- survey$Height[survey$Sex == "Male"   & !is.na(survey$Height)];
womens.heights <- survey$Height[survey$Sex == "Female" & !is.na(survey$Height)];
stem.leaf.backback(mens.heights, womens.heights, m = 10, depths = FALSE);

# 7
valid <- !is.na(survey$Age) & !is.na(survey$Sex);
ages <- survey$Age[valid];
sex  <- survey$Sex[valid];

rng   <- range(ages);
breaks <- seq(floor(rng[1]), ceiling(rng[2]), by = 2);

male_ages   <- ages[sex == "Male"];
female_ages <- ages[sex == "Female"];

male_class   <- cut(male_ages, breaks = breaks, right = TRUE, include.lowest = TRUE);
female_class <- cut(female_ages, breaks = breaks, right = TRUE, include.lowest = TRUE);

male_freq   <- cbind(Male   = table(male_class));
female_freq <- cbind(Female = table(female_class));

male_freq;
female_freq;

# 8
hist(
  survey$Height,
  breaks = seq(150, 200, by = 2.5),
  xlab   = "Heights (cm)",
  ylab   = "Frequency",
  main   = paste("Heights of", length(survey$Height), "students"),
  col    = "white"
);

# 9
h <- hist(survey$Height, 
          breaks = seq(150, 200, by = 2.5), 
          plot = FALSE
          );

plot(h$breaks, 
     c(0, cumsum(h$counts)), 
     type = "b", 
     pch = 1,
     xlab = "student heights in cm",
     ylab = "number of students",
     main = "Ogive of 209 student heights"
     );

# 10
library(mosaic);
xyplot(survey$Wr.Hnd ~ survey$Height,
       xlab = "Student Height (cm)",
       ylab = "Writing Hand Span (cm)",
       main = "Scatterplot of Hand Span vs Height"
       );

# 11
# Wr.Hnd vs NW.Hnd
xyplot(survey$Wr.Hnd ~ survey$NW.Hnd,
       xlab = "Non-writing Hand Span (cm)",
       ylab = "Writing Hand Span (cm)",
       main = "Scatterplot of Writing Hand vs Non-writing Hand"
       );

# Height vs Pulse
xyplot(survey$Height ~ survey$Pulse,
       xlab = "Pulse Rate (beats per minute)",
       ylab = "Height (cm)",
       main = "Scatterplot of Height vs Pulse"
       );

