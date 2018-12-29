接上篇[Maven 构建 spring boot v2 + spring mvc + MyBatis](/article/a05e5700-7554-11e8-8dac-292d5a5525b5)，下面继续实现Mybatis的Mapper映射数据库表

#### 新建一个module demo2

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.modules</groupId>
    <artifactId>demo2</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>demo2</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.0.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>compile</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.2</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

    <repositories><!-- 代码库 -->
        <repository>
            <id>maven-ali</id>
            <url>http://maven.aliyun.com/nexus/content/groups/public//</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
                <checksumPolicy>fail</checksumPolicy>
            </snapshots>
        </repository>
    </repositories>
</project>

```

#### spring-boot 配置

```
server.port=38083

# 配置所有路由的前缀
#server.servlet.context-path=/api

# 配置静态路由
spring.mvc.static-path-pattern=/static/**
# 配置静态路由映射目录
spring.resources.static-locations=classpath:static, classpath:public

# 配置thymeleaf 模版
#spring.thymeleaf.mode=HTML
#spring.thymeleaf.encoding=UTF-8
#spring.thymeleaf.servlet.content-type=text/html
#spring.thymeleaf.prefix=classpath:/templates/

# mysql数据库配置
spring.datasource.url=jdbc:mysql://mysqlhost:3306/bqj_evidence
spring.datasource.username=mysqlUserName
spring.datasource.password=mysqlUserNamePassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# 初始化执行脚本
spring.datasource.initialization-mode=always
spring.datasource.platform=mysql
# 配置执行的初始化的sql脚本文件
spring.datasource.schema=classpath:schema-${spring.datasource.platform}.sql

# mybatis mappers 路径
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
# 执行文件配置不能和mybatis.configuration同时指定
#mybatis.config-location=classpath:mybatis/config.xml

# mybatis 配置
mybatis.configuration.cache-enabled=true
mybatis.configuration.lazy-loading-enabled=true
mybatis.configuration.aggressive-lazy-loading=true
mybatis.configuration.multiple-result-sets-enabled=true
mybatis.configuration.use-column-label=true
mybatis.configuration.use-generated-keys=true
mybatis.configuration.auto-mapping-behavior=partial
mybatis.configuration.default-executor-type=simple
mybatis.configuration.local-cache-scope=session
mybatis.configuration.jdbc-type-for-null=null
mybatis.configuration.log-impl=org.apache.ibatis.logging.slf4j.Slf4jImpl
mybatis.configuration.map-underscore-to-camel-case=true
mybatis.configuration.default-fetch-size=100
mybatis.configuration.default-statement-timeout=30

# 日志级别
logging.level.root=WARN
# 输出日志的package
logging.level.com.modules.demo2=debug
```

其中 `schema-mysql.sql` 如下

```
create table if not exists  test(id int primary key auto_increment, name varchar(50), email varchar(50));

insert into test(name, email) values ('cl', 'cl@126.com');
insert into test(name, email) values ('cl2', 'cl2@126.com');
insert into test(name, email) values ('cl3', 'cl3@126.com');
```

#### mapper实现方式

首先定义 `TestEntity` 

```
@Getter
@Setter
public class TestEntity implements Serializable {
    private int id;
    private String name;
    private String email;
    private Date updateDate;
}

```

spring配置文件中配置了 `mybatis.mapper-locations=classpath:mybatis/mapper/*.xml`，那么在 `classpath:mybatis/mapper`目录下的xml文件都会被认为是mybatis的mapper，下面新建一个 `TestDao.xml`

```
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.modules.demo2.dao.TestDao">
   
       <sql id="testColumns">
        id,
        name,
        email
    </sql>
   
       <select id="get" resultType="com.modules.demo2.entity.TestEntity">
           SELECT
           <include refid="testColumns"/>
           FROM test
           WHERE id = #{id}
       </select>
   
       <select id="findList" resultType="com.modules.demo2.entity.TestEntity">
           SELECT
           <include refid="testColumns"/>
           FROM test
           <where>
               <if test="id != null">
                AND id = #{id}
               </if>
           </where>
       </select>
   
       <select id="findAllList" resultType="com.modules.demo2.entity.TestEntity">
           SELECT
           <include refid="testColumns"/>
           FROM test
       </select>
   
       <insert id="insert" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO test(
            id,
            name,
            email
        ) VALUES (
            #{id},
            #{name},
            #{email}
        )
    </insert>
   
       <update id="update">
        UPDATE test SET
            name=#{name},
            email=#{email}
        WHERE id = #{id}
    </update>
   
   </mapper>
```

定义 `TestDao.java`

```
   @Mapper
   public interface TestDao extends CrudDao<TestEntity> {
   
       @Select("select * from test where name = #{name2}")
       List<TestEntity> findByName(@Param("name2") String name);
   }

```

定义 `TestService.java`    
    
```
@Service
public class TestService {

    @Autowired
    private TestDao testDao;

    public TestEntity get(String id){
        TestEntity testEntity = new TestEntity();
        testEntity = testDao.get(id);
        return testEntity;
    }

    public List<TestEntity> findByName(String name){
        return testDao.findByName(name);
    }
}
```

最后定义 `TestController`

```
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test2")
    TestEntity getUserByName2(HttpServletRequest req, HttpServletResponse res){
        TestEntity testEntity = testService.get(req.getParameter("id"));
        return testEntity;
    }

    @RequestMapping("/test3")
    List<TestEntity> findByName(String name){
        List<TestEntity> testList = testService.findByName(name);
        return testList;
    }
}
```

------------

#### 打包、启动、访问测试

```
mvn clean install
java -jar target/demo2-0.0.1-SNAPSHOT.jar
curl localhost:38083/test?name=cl
```

返回

```
[{"id":286,"name":"cl","email":"cl@126.com"},{"id":289,"name":"cl","email":"cl@126.com"}]
```

可知，mapper中的sql可以定义在方法上面，也可以定义在xml中。

