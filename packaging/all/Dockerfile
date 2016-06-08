FROM jeanblanchard/busybox-java:8
COPY chat-all.jar /app/chat-all.jar
COPY etc /app/etc/
WORKDIR /app
ENTRYPOINT ["java","-jar","/app/chat-all.jar"]
