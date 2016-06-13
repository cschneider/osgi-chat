FROM jeanblanchard/busybox-java:8
COPY consumer.jar /app/irc.jar
COPY etc /app/etc/
WORKDIR /app
ENTRYPOINT ["java","-jar","/app/irc.jar"]
