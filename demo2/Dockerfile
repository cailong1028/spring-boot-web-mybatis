#Dockerfile
# 基础镜像
FROM java:8-jre-alpine
# RUN echo http://mirrors.ustc.edu.cn/alpine/v3.7/main > /etc/apk/repositories && \
# echo http://mirrors.ustc.edu.cn/alpine/v3.7/community >> /etc/apk/repositories
# 共享的宿主机目录
VOLUME /tmp
# 将项目打包jar文件拷贝到指定位置
COPY ./demo2-0.0.1-SNAPSHOT.jar /app.jar
# 拷贝生产环境配置文件
COPY ./application.docker.properties /application.properties
#COPY simsun.ttf /usr/share/fonts/simsun.ttf
# 时区
ENV TZ=Asia/Shanghai
# RUN apt-get install tzdata \
# RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# RUN bash -c 'touch /app.jar'
# 查询宿主机host和ip，写入docker /etc/hosts，经实践，没有必要
RUN /sbin/ip route|awk '/default/ { print  $3,"\tdockerhost" }' >> /etc/hosts
# 暴露项目端口
EXPOSE 38083
# 启动时，设置项目配置文件为替换过的配置
ENTRYPOINT ["java","-jar","/app.jar","--spring.config.location=/application.properties"]