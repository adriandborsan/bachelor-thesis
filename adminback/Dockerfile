FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY . .
RUN --mount=type=cache,target=/root/.gradle ./gradlew bootJar &&\
    cd build/libs &&\
    java -Djarmode=layertools -jar ./*.jar extract


FROM eclipse-temurin:17-jre-jammy
ARG PATH=/app/build/libs
WORKDIR ${PATH}
COPY --from=builder ${PATH}/dependencies/ ./
COPY --from=builder ${PATH}/spring-boot-loader/ ./
COPY --from=builder ${PATH}/snapshot-dependencies/ ./
COPY --from=builder ${PATH}/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
