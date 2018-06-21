#### mvn 构建 spring boot v2 + spring mvc + mybatis

##### 安装jdk8

到[Oracle官网下载](http://www.oracle.com)，并安装 (略)

##### 安装，配置Maven，并设置maven的远程仓库地址

- windows下
    
    ![](/images/180621/0.png)
    
    解压后将`bin`目录添加到环境变量(略)
    
    查看Maven是否配置成功
    
    ```
    mvn -version
    ```
- mac
    
    ```
    brew install maven
    ```
    
- 修改`maven/conf/settings.xml`，添加ali的maven仓库地址，这样比较块
  		
    ```
  	<mirrors>
        <mirror>
            //该镜像的id
            <id>nexus-aliyun</id>
            //该镜像用来取代的远程仓库，central是中央仓库的id
            <mirrorOf>central</mirrorOf>
            <name>Nexus aliyun</name>
            //该镜像的仓库地址，这里是用的阿里的仓库
            <url>http://maven.aliyun.com/nexus/content/groups/public</url>
        </mirror>
    </mirrors>
    ```

- 如果需要添加仓库中无法获取的jar，添加外部依赖，比如添加`src/lib目录下的ldapjdk.jar`

    ```
	<dependency>
         <groupId>ldapjdk</groupId>
         <artifactId>ldapjdk</artifactId>
         <scope>system</scope>
         <version>1.0</version>
         <systemPath>${basedir}\src\lib\ldapjdk.jar</systemPath>
      </dependency>
    ```

##### 安装mysql并添加一个对指定数据库权限的用户

操作系统linux centos7

1. 安装从网上下载文件的wget命令

    ```
    sudo yum -y install wget
    ```
2. 下载mysql的repo源

    ```
    wget http://repo.mysql.com/mysql-community-release-el7-5.noarch.rpm
    ```
  
3. 安装mysql-community-release-el7-5.noarch.rpm包
    
    ```
    rpm -ivh mysql-community-release-el7-5.noarch.rpm
    ```

4. 查看下
    
    ```
    ls -1 /etc/yum.repos.d/mysql-community*
    /etc/yum.repos.d/mysql-community.repo
    /etc/yum.repos.d/mysql-community-source.repo
    ```
    
    会获得两个mysql的yum repo源：/etc/yum.repos.d/mysql-community.repo，/etc/yum.repos.d/mysql-community-source.repo。

5. 安装mysql

    ```
    sudo yum install mysql-server 
    ``` 
    
6. root初始登陆，创建一个新的用户

    ```
    mysql -uroot
    grant all on db_test.* to cl@'%' identified by 'cl';
    #允许本地登陆
    grant all on db_test.* to cl@'localhost';
    ```

    创建数据库，可通过Navicat建立名为`db_test`的数据库，设置数据库编码`utf8` (略)

7. 防火墙开放3306端口

    修改`/etc/firewalld/zones/public.xml`，添加3306端口
    重启防火墙
    ```
    sudo systemctl restart firewalld
    ```
    
##### 初步构建系统

1. [官网](http://spring.io/)

![](/images/180621/1.png)

2. [spring项目集合地址](http://spring.io/projects)

![](/images/180621/2.png)

3. [spring-boot](https://spring.io/projects/spring-boot)

![](/images/180621/3.png)

4. [spring-boot项目构建地址](https://start.spring.io/)

![](/images/180621/4.png)

随后就下载了包含了`Web`和`MyBatis`的压缩包，是一个普通的Maven的项目目录结构


##### 添加jdbc-mysql

![](/images/180621/5.png)

![](/images/180621/6.png)

![](/images/180621/7.png)

拷贝到pom

##### 配置`application.properties` 

```
spring.datasource.url=jdbc:mysql://your-mysql-host:3306/db_test?useUnicode=true&characterEncoding=utf8
spring.datasource.username=cl
spring.datasource.password=cl

# command line 方式执行 java -jar *.jar --server.port=8081
# 也可以设置外部application.properties，详见 [springboot中配置文件application.properties的理解](https://www.cnblogs.com/shamo89/p/8178109.html)
server.port=8081

# http://www.mybatis.org/mybatis-3/configuration.html#settings
#mybatis.configuration.log-impl=org.apache.ibatis.logging.slf4j.Slf4jImpl

# debug下才会输出mybatis的sql
logging.level.com.cl=debug
```

上面的配置中`spring.datasource`是关于数据库的，`logging.level`是日志输出级别，当`debug`时，MyBatis的sql才会输出

关于对`application.properties`文件的理解，详见[springboot中配置文件application.properties的理解](https://www.cnblogs.com/shamo89/p/8178109.html)，这篇文章里面`自定义属性`, `外部配置-命令行参数配置`, `配置文件的优先级`, 都很有用

关于MyBatis的其他配置，详见[MyBatis-sprint-boot-starter 官网](http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)

![](/images/180621/8.png)

官网有两个`MyBatis-spring-boot-starter`例子

![](/images/180621/9.png)

[例子1：spring-boot-starter/mybatis-spring-boot-samples/mybatis-spring-boot-sample-annotation/](https://github.com/mybatis/spring-boot-starter/tree/master/mybatis-spring-boot-samples/mybatis-spring-boot-sample-annotation)

[例子2：spring-boot-starter/mybatis-spring-boot-samples/mybatis-spring-boot-sample-xml/](https://github.com/mybatis/spring-boot-starter/tree/master/mybatis-spring-boot-samples/mybatis-spring-boot-sample-xml)

[MyBatis-3官网地址](http://www.mybatis.org/mybatis-3/)


----------------------

##### 代码部分

首先数据库中新建一个`User`表，然后创建与之对应的`domain`和`Mapper`

![](/images/180621/10.png)

###### user

```
package com.cl.springbootwebmybatis.domain;

import java.io.Serializable;

public class User implements Serializable{
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
```

###### UserMapper

```
package com.cl.springbootwebmybatis.mapper;

import com.cl.springbootwebmybatis.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where name = #{name}")
    User findByName(@Param("name") String name);
}
```

###### SpringBootWebMybatisApplication

```
package com.cl.springbootwebmybatis;

import com.cl.springbootwebmybatis.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootWebMybatisApplication implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(SpringBootWebMybatisApplication.class);

	final private UserMapper userMapper;

	public SpringBootWebMybatisApplication(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public static void main(String[] args) {
	    SpringApplication.run(SpringBootWebMybatisApplication.class, args);
	    log.info("app start!");
	}

	@Override
	public void run(String... args) throws Exception {
		log.info(this.userMapper.findByName("admin").getEmail());
	}
}
```

##### Maven测试，编译，打包

```
mvn compile
mvn package
``` 

会在`target`目录生成快照jar包

##### 执行

```
jar -jar spring-boot-web-mybatis-0.0.1-SNAPSHOT.jar
```

启动成功，可以看到其执行了sql，并返回了一条数据

![](/images/180621/11.png)

---------------------------------------------------------

参考链接
 
 [linux centos7下解决yum install mysql-server没有可用包](https://www.cnblogs.com/yowamushi/p/8043054.html)
 
 