FROM jeanblanchard/busybox-java:8
COPY display.jar /app/display.jar
COPY etc /app/etc/
WORKDIR /app
ENTRYPOINT ["java","-Daries.rsa.hostname=display", "-jar","/app/display.jar"]
