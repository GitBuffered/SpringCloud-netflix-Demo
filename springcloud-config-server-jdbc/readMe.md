config支持多种数据配置，包含git、svn、vault、 jdbc, 这里我推荐使用JDBC的方式，因为之前的GIT SVN 
对于服务比较少的系统，可能比较容易维护，如果服务比较多，没有一个后台管理系统来维护，就太复杂了，在我的水平上理想的架构是这样的： 





### config的数据库配置

config支持多种数据配置，包含git、svn、vault、 jdbc, 这里我推荐使用JDBC的方式，因为之前的GIT SVN 
对于服务比较少的系统，可能比较容易维护，如果服务比较多，没有一个后台管理系统来维护，就太复杂了，在我的水平上理想的架构是这样的： 
![描述](https://img-blog.csdn.net/20180117234326002?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdGlhbjYzMTg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

### config数据库配置

1.查看spring-cloud-config-server.jar中的源码，找到EnvironmentRepositoryConfiguration.java，这个是server的注入类 
![源码](https://img-blog.csdn.net/20180117234828609?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdGlhbjYzMTg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast) 
![源码](https://img-blog.csdn.net/20180117235015962?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdGlhbjYzMTg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

阅读源码，我们可以知道，config默认支持git模式，但是同时也提供了svn、vault、 jdbc，三种配置模式，那我们怎么激活呢？

```
spring.profiles.active=jdbc1
```

同时，我们查看JdbcEnvironmentRepository.java 
![源码2](https://img-blog.csdn.net/20180117235255493?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdGlhbjYzMTg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

发现，是通过JdbcTemplate 来读取的的数据库，同时，JdbcEnvironmentRepository  
也提供了默认的数据库查询SQL:

```
SELECT KEY, VALUE from PROPERTIES where APPLICATION=? and PROFILE=? and LABEL=?1
```

同时 sql提供了 set方法，说明SQL 是可以自定义的，好了，那我们就按照源码的样子来配置吧.

2.这里我们用mysql,新建数据库test,在test库下面新建表properties ，建表语句如下：

```
CREATE TABLE `properties` (
  `id` int(11) NOT NULL,
  `key` varchar(50) DEFAULT NULL,
  `value` varchar(500) DEFAULT NULL,
  `application` varchar(50) DEFAULT NULL,
  `profile` varchar(50) DEFAULT NULL,
  `lable` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;123456789
```

3.新建cloud-config-jdbc-server项目，引入依赖配置

```
<!-- mysql 依赖包 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.38</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

4.配置 application.properties，这里我们重写SQL，因为KEY为mysql关键字，在mysql需要使用反引号来表示其为字段，配置如下：

```
server.port=8082
spring.application.name=cloud-config-jdbc-server
#数据库配置
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.cloud.config.label=master
spring.cloud.config.server.jdbc=true
spring.cloud.config.server.jdbc.sql=SELECT `KEY`, `VALUE` from PROPERTIES where APPLICATION=? and PROFILE=? and LABEL=?
spring.profiles.active=jdbc
```

5.给启动类加上@EnableConfigServer 注解

```
@EnableConfigServer
@SpringBootApplication
public class CloudConfigJdbcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudConfigJdbcServerApplication.class, args);
    }
}
```

6.我们在数据库中新增一条数据 
![数据](https://img-blog.csdn.net/20180118000753369?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdGlhbjYzMTg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

7，启动项目,访问[http://127.0.0.1:8082/cloud-client/test/master](http://127.0.0.1:8082/cloud-client/test/master) 
![结果](https://img-blog.csdn.net/20180118001420227?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdGlhbjYzMTg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast) 
结果访问到数据库的值。

8.当然后面也可以改造重写该类，比如，可以在数据库层再加一层缓存，这样性能就更高了。
