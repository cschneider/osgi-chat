FROM jeanblanchard/busybox-java:8
COPY irc.jar /app/irc.jar
COPY etc /app/etc/
WORKDIR /app
ENTRYPOINT ["java","-Daries.rsa.hostname=irc", "-jar","/app/irc.jar"]
