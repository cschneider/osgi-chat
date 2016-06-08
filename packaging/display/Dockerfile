FROM jeanblanchard/busybox-java:8
COPY service.jar /app/service.jar
COPY etc /app/etc/
WORKDIR /app
ENTRYPOINT ["java","-jar","/app/service.jar", "-Daries.rsa.hostname=service"]
EXPOSE 8201 2181
