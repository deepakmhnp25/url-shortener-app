FROM openjdk:17
VOLUME /tmp
ADD target/url-shortener-app-0.0.1-SNAPSHOT.jar shortener-app.jar
ENTRYPOINT ["java","-jar","/shortener-app.jar"]