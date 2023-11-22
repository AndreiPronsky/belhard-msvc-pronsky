FROM amazoncorretto:17-alpine3.18 as JAR_EXTRACT

# set working directory
WORKDIR /project

# copy the packaged jar file into our docker image
COPY build/libs/song-service-0.0.1-SNAPSHOT.jar /project

# extract jar file
RUN java -Djarmode=layertools -jar song-service-0.0.1-SNAPSHOT.jar extract

FROM amazoncorretto:17-alpine3.18
WORKDIR /application

# specify container directory for DB data storage
VOLUME var/lib/postgresql/data/songs

# expose required ports
EXPOSE 8080

# every copy command is creating new layer in the image
COPY --from=JAR_EXTRACT /project/dependencies/. /application/.
COPY --from=JAR_EXTRACT /project/spring-boot-loader/. /application/.
COPY --from=JAR_EXTRACT /project/snapshot-dependencies/. /application/.
COPY --from=JAR_EXTRACT /project/application/. /application/.
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
