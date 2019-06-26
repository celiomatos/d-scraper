FROM openjdk:8u191-jre-alpine3.8
WORKDIR /app
COPY d-scraper.war .
CMD echo "172.17.0.2      d-server-db" >> /etc/hosts && java -jar d-scraper.war