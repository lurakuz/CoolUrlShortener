FROM openjdk:17-oracle

COPY docker/CoolUrlShortener-1.0-SNAPSHOT.jar /coolurlshortener.war

CMD ["/usr/bin/java", "-jar", "/coolurlshortener.war"]