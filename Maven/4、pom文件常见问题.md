### 1. 设置源码为UTF-8格式
```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```
### 2. 设置源码编辑JDK版本
```xml
<plugin>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <source>1.8</source>
        <target>1.8</target>
    </configuration>
</plugin>
```
### 3. jar包不想包含META-INF/maven文件夹（pom.properties，pom.xml文件）
```xml
<plugin>
    <artifactId>maven-jar-plugin</artifactId>
    <configuration>
        <archive>
            <addMavenDescriptor>false</addMavenDescriptor>
        </archive>
    </configuration>
</plugin>
```
### 4. jar中META-INF/MANIFEST.MF 文件中指定main类
```xml
<plugin>
    <artifactId>maven-jar-plugin</artifactId>
    <configuration>
        <archive>
        <addMavenDescriptor>false</addMavenDescriptor>
        <manifest>
            <mainClass>com.xuanwu.mtoserver.util.Test</mainClass>
        </manifest>
        </archive>
    </configuration>
</plugin>
```
### 5. 打包时生成源码包-sources.jar
```xml
<plugin>
    <artifactId>maven-source-plugin</artifactId>
    <executions>
        <execution>
            <id>attach-sources</id>
            <goals>
                <goal>jar-no-fork</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
### 6. 设置打包后的文件名
```xml
<build>
    <finalName>mcp</finalName>
</build>
```