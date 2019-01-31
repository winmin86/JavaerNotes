这个Maven的项目主要是讲pom的语法和配置，至于maven怎么安装的就不讲了。

```markdown
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.company.bank</groupId>
    <artifactId>consumer-banking</artifactId>
    <version>1.0</version>
</project>
```
- 要注意的是，每个项目只有一个POM文件。
- 所有的 POM 文件要项目元素必须有三个必填字段: groupId，artifactId，version。
- 在库中的项目符号是：groupId:artifactId:version。
- pom.xml 的根元素是 project。


节点|描述
---|---
groupId | 这是项目组的编号，这在组织或项目中通常是独一无二的。 例如，一家银行集团com.company.bank拥有所有银行相关项目。
artifactId | 这是项目的ID。这通常是项目的名称。 例如，consumer-banking。 除了groupId之外，artifactId还定义了artifact在存储库中的位置。
version | 这是项目的版本。与groupId一起使用，artifact在存储库中用于将版本彼此分离。 例如：com.company.bank:consumer-banking:1.0，com.company.bank:consumer-banking:1.1





