Spring Cloud Config配置文件加解密
坑爹的问题

> curl http://localhost:8888/encrypt -d 123
 {"description":"No key was installed for encryption service","status":"NO_KEY"}
 
 遇到这个问题有三个原因

    未配置JCE
    未设置key/keystore
    spring cloud bug
 如何解决
覆盖JCE

JDK6的下载地址：
http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html

JDK7的下载地址：
http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html

JDK8的下载地址：
http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

下载后，加压文件，把local_policy.jar,US_export_policy.jar拷贝并覆盖到$JAVA_HOME/jre/lib/security
设置key/keystore
设置key

在application.properties设置对应的key

encrypt.key=mykey



设置keystore

生成keystore

$ keytool -genkeypair -alias mytestkey -keyalg RSA \
  -dname "CN=Web Server,OU=Unit,O=Organization,L=City,S=State,C=US" \
  -keypass changeme -keystore server.jks -storepass letmein



修改application.yml

encrypt:
  keyStore:
    location: classpath:/server.jks
    password: letmein
    alias: mytestkey
    secret: changeme



spring cloud bug

Dalston.SR3、Dalston.SR2版本不能对配置文件加密，若需要调整到Dalston.SR1或者期待Dalston.SR4的发布