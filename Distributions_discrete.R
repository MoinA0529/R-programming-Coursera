#####################################################
# DISCRETE UNIFORM 
#####################################################

n<- 1000 # sample size 
sp<- 5:10 # sample space 
x<-sample(sp, n, replace=T)   # data 
par(mfrow=c(1,1))
barplot(table(x)/n, col=4, ylim=c(0,1), main = " DISCRETE UNIFORM ")

#####################################################
# BINOMIAL(m,p)
# It is the total number of  successes  in a series of  m Bernoulli events.
#####################################################

n<- 10000 # sample size 
m<-10
p<-0.7
x1<-rbinom(n,m,p)   # data 
par(mfrow=c(1,1))
plot(table(x1)/n, col=3, xlim=c(0,m), main = " BINOMIAL(m,p) ")
lines(dbinom(0:m, m,p)~c(0:m), type='p', col=2, lwd=2)
# Binomoal from Uniform(0,1)
x2 <- array(0,dim=c(n))
for (i in 1:n) {
  x2[i] <- sum(runif(m)<p)
}
par(mfrow=c(2,1))
barplot(table(x1)/n, col=3, ylim=c(0,1), xlim=c(0,m), xlab='X1', main = " BINOMIAL(m,p) ")
barplot(table(x2)/n, col=2, ylim=c(0,1), xlim=c(0,m), xlab='X2', main = " BINOMIAL(m,p) ")

#####################################################
# GEOMETRIC(p)
# It is the number of failures before a success in a series of Bernoulli event.
#####################################################

n<- 10000 # sample size 
p<-0.3
x1<-rgeom(n,p)   # data 
par(mfrow=c(1,1))
plot(table(x1)/n, col=3, ylim=c(0,1), main = " GEOMETRIC(p)")
lines(dgeom(0:max(x1),p)~c(0:max(x1)), type='p', col=2, lwd=2)
# Geometric from Uniform(0,1)
x2 <- array(0,dim=c(n))
for(i in 1 :n){
count<-0
s<-0
while (s==0) {
  
  count=count+1
  s<-(runif(1)<p)
}
x2[i]<-count-1
}
par(mfrow=c(2,1))
barplot(table(x1)/n, col=3, ylim=c(0,1), xlim=c(0,m), xlab='X1', main = "  GEOMETRIC(p) ")
barplot(table(x2)/n, col=2, ylim=c(0,1), xlim=c(0,m), xlab='X2', main = " GEOMETRIC(p) ")

#####################################################
# NEGTIVE BINOMIAL(r,p)
# It is the number of  failures  before a success in a series of Bernoulli events.
#####################################################

n<- 10000 # sample size 
r<-5
p<-0.3
x1<-rnbinom(n,r,p)   # data 
par(mfrow=c(1,1))
plot(table(x1)/n, col=3, main = " NEGTIVE BINOMIAL(r,p)")
lines(dnbinom(0:max(x1),r,p)~c(0:max(x1)), type='p', col=2, lwd=2)
# NEGTIVE BINOMIAL(r,p) as a sum of r independent  GEOMETRIC(p)

x2 <- array(0,dim=c(n))
for(i in 1 : n){
  x2[i]<-sum(rgeom(r,p))
}

par(mfrow=c(2,1))
barplot(table(x1)/n, col=3, xlab='X1', main = " NEGTIVE BINOMIAL(r,p) ")
barplot(table(x2)/n, col=2, xlab='X2', main = " NEGTIVE BINOMIAL(r,p) ")

#####################################################
# POISSON(lambda)
#####################################################

n<- 10000 # sample size 
lambda<-2.3
x1<-rpois(n, 2.3)   # data 
par(mfrow=c(1,1))
plot(table(x1)/n, col=3, main = " POISSON(lambda)")
lines(dpois(0:max(x1),lambda)~c(0:max(x1)), type='p', col=2, lwd=2)
# Poisson as  a limit of binomial 
n<- 10000 # sample size 
m<-100
p<-.023
x2<-rbinom(n,m,p)   # data 
par(mfrow=c(2,1))
barplot(table(x1)/n, col=3, xlab='X1', main = " POISSON(lambda) ")
barplot(table(x2)/n, col=2, xlab='X2', main = " POISSON(lambda) ")
cat("\newpage")

##############################
# Multinomial(k,p_vector)
##############################

k <- 5  # categories
n <- 1000 # sample size 
p <- c(.2,.5,.1,.1,.1) # probbility vector 
x1 <- sample(1:k, n, replace=T, prob=p) # data 
print(table(x1)/n )
par(mfrow=c(1,1))
plot(table(x1)/n,ylim=c(0,1), col=3, main = " Multinomial(k,p_vector) ")
lines(p, type='p', col=2, lwd=2)
