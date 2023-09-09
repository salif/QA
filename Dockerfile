FROM openjdk:11.0.7-jre
EXPOSE 8080
COPY ./target/qa-0.0.2-SNAPSHOT.jar /bin
CMD ["java", "-jar", "/bin/qa-0.0.2-SNAPSHOT.jar"]
