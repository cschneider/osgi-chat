FROM jeanblanchard/busybox-java:8
COPY consumer.jar /app/consumer.jar
COPY etc /app/etc/
WORKDIR /app
ENTRYPOINT ["java","-jar","/app/consumer.jar"]
