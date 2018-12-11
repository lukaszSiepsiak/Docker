FROM java:8

LABEL maintainer="karolina piskorska"
COPY . /
WORKDIR /  
RUN javac DockerfileSQL.java
CMD ["java", "-classpath", "mysql-connector-java-5.1.6-bin.jar:.","DockerfileSQL"]
