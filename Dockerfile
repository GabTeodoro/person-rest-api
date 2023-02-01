FROM openjdk:11
EXPOSE 8100
WORKDIR /src
COPY /target/*.jar /src/app.jar
ENTRYPOINT ["java", "-Xmx512m", "-jar", "/src/app.jar"]