FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER jeecgos@163.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
VOLUME ["/www/wwwroot/47.98.228.176/upload","/opt/jeecg-boot/upload"]
VOLUME ["/www/wwwroot/47.98.228.176/webapp","/opt/jeecg-boot/webapp"]
#RUN mkdir -p /jeecg-boot/config/jeecg/

WORKDIR /jeecg-boot

EXPOSE 8080

#ADD ./src/main/resources/jeecg ./config/jeecg
ADD ./target/jeecg-system-start-3.6.1.jar ./
ADD ./src/main/resources/application-prod.yml ./
CMD java -Djava.security.egd=file:/prod/./urandom -jar jeecg-system-start-3.6.1.jar --spring.config.location=application-prod.yml