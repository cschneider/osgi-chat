FROM jeanblanchard/busybox-java:8
COPY target/irc.jar /app/irc.jar
COPY etc /app/etc/
WORKDIR /app
ENTRYPOINT ["java","-Daries.rsa.hostname=irc", "-jar","/app/irc.jar"]
