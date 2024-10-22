FROM openjdk:17
EXPOSE 8080
ADD target/SaadMVC.jar SaadMVC.jar
ENTRYPOINT ["java", "-jar", "/SaadMVC.jar"]
