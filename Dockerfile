FROM openjdk:21
EXPOSE 8080
ADD target/SaadMVC.jar SaadMVC.jar
ENTRYPOINT ["java","-jar","/bookstore.jar"]
