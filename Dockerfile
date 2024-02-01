FROM eclipse-temurin:17-jre-alpine
COPY /target/lottery.jar /lottery.jar
ENTRYPOINT ["java","-jar","/lottery.jar"]