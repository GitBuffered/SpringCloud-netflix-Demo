#Feign
##loggin Feign日志的配置

    为每个创建的Feign客户端创建一个记录器。默认情况下，记录器的名称是用于创建Feign客户端的接口的完整类名。Feign日志记录仅响应DEBUG级别。logging.level.project.user.UserClient: DEBUG
在配置文件application.yml 中加入：
~~~
logging:
 level:
  com.jalja.org.spring.simple.dao.FeignUserClient: DEBUG 
~~~
在自定义的Configuration的类中添加日志级别
~~~

@Configuration
public class FooConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {
        //设置日志
        return Logger.Level.FULL;
    }
}
~~~
    NONE, No logging (DEFAULT).
    BASIC, Log only the request method and URL and the response status code and execution time.
    HEADERS, Log the basic information along with request and response headers.
    FULL, Log the headers, body, and metadata for both requests and responses.
    
    