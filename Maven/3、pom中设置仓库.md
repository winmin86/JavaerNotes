```xml
    <repositories>
    <!-- 配置nexus远程仓库 -->
        <repository>
            <id>nexus</id>
            <name>Nexus Snapshot Repository</name>
            <url>http://127.0.0.1:8088/nexus/content/groups/public/</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>
    <!-- 配置从哪个仓库中下载构件，即jar包 -->
    <pluginRepositories>
        <pluginRepository>
            <id>nexus</id>
            <name>Nexus Snapshot Repository</name>
            <url>http://127.0.0.1:8088/nexus/content/groups/public/</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </pluginRepository>
    </pluginRepositories>
```