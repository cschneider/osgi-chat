FROM jeanblanchard/busybox-java:8
COPY service.jar /app/display.jar
COPY etc /app/etc/
WORKDIR /app
ENTRYPOINT ["java","-jar","/app/display.jar", "-Daries.rsa.hostname=display"]
