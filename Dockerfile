FROM alpine:latest
EXPOSE 8083
RUN apk --update --no-cache add ca-certificates openjdk11
RUN mkdir /app
ADD lottery*.jar /app/proposta.jar

ENTRYPOINT ["java", "-jar", "/app/proposta.jar"]