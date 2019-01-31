**compile(编译范围):**
compile是默认的范围；如果没有提供一个范围，编译范围依赖在所有的classpath 中可用，同时它们也会被打包。而且这些dependency会传递到依赖的项目中。

**provided(已提供范围):**
跟compile相似，但是表明了dependency 由JDK或者容器提供。例如如果开发了一个web 应用，可能在编译 classpath 中需要可用的Servlet API 来编译一个servlet，但是你不会想要在打包好的WAR 中包含这个Servlet API；这Servlet API JAR 由你的应用服务器或者servlet容器提供。已提供范围的依赖在编译classpath （不是运行时）可用。它们不是传递性的也不会被打包。
```markdown
<dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>jsp-api</artifactId>
    <version>2.1</version>
    <scope>provided</scope>
</dependency>
```

**runtime(运行时范围):**
runtime 依赖在运行和测试系统的时候需要，但在编译的时候不需要。比如可能在编译的时候只需要JDBC API JAR，而只有在运行的时候才需要JDBC驱动实现。

**test(测试范围):**
表示dependency作用在测试时，不作用在运行时。 只在测试时使用，用于编译和运行测试代码。不会随项目发布。
```markdown
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.11</version>
    <scope>test</scope>
</dependency>
```

**system(系统范围):**
system范围依赖与provided 类似，但是你必须显式的提供一个对于本地系统中JAR 文件的路径。这么做是为了允许基于本地对象编译，而这些对象是系统类库的一部分。这样的构件应该是一直可用的，Maven 也不会在仓库中去寻找它。如果你将一个依赖范围设置成系统范围，你必须同时提供一个 systemPath 元素。注意该范围是不推荐使用的（你应该一直尽量去从公共或定制的 Maven 仓库中引用依赖）。

**import(导入):**
从其它的pom文件中导入依赖设置。

