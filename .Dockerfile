FROM java:8-jdk

RUN apt-get update \
    && apt-get install -y git

RUN git clone https://github.com/Bajrangjangid/movie-booking.git \
    && cd movie-booking \
    && chmod +x mvnw \
    && ./mvnw -DskipTests=true package

ADD ./target/movie-booking-0.0.1-SNAPSHOT.jar /root/
CMD ["java", "-jar", "/root/movie-booking-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080