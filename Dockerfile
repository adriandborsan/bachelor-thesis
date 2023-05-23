# FROM eclipse-temurin:17-jdk-alpine
# COPY build/libs/kiire-back-1.jar app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]


#For multistage docker uncomment the following:

FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

#   can copy files that rarely modifies separately from the source folder

#COPY gradle gradle
#COPY build.gradle settings.gradle gradlew ./
#COPY src src

# could also just copy all files because in the dockerignore file all the irrrelevant files are ignored
COPY . .

# chaining commands so we can have only one layer
# --mount=type=cache,target=/root/.gradle is NOT necessary
#but stops from downloading gradles binaries evey time we build
# ./gradlew could use a --no-daemon argument, but does not impact performance too much
# bootJar can be replaced with build so the application is also tested
RUN --mount=type=cache,target=/root/.gradle ./gradlew bootJar &&\
   cd build/libs &&\
   java -Djarmode=layertools -jar ./*.jar extract





## This is an alternative to the previous builder
## Using this, we can also dockerignore gradle folder and gradlew file
#
#FROM gradle:7.5.1-jdk17 as builder
#WORKDIR /app
#COPY build.gradle settings.gradle /app/
#RUN gradle clean build --no-daemon || true
#COPY ./ /app/
#RUN --mount=type=cache,target=/root/.gradle gradle bootJar --no-daemon &&\
#    cd build/libs &&\
#    java -Djarmode=layertools -jar ./*.jar extract

FROM eclipse-temurin:17-jre-jammy
ARG PATH=/app/build/libs
WORKDIR ${PATH}
COPY --from=builder ${PATH}/dependencies/ ./
COPY --from=builder ${PATH}/spring-boot-loader/ ./
COPY --from=builder ${PATH}/snapshot-dependencies/ ./
COPY --from=builder ${PATH}/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
