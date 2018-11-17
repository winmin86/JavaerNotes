package xyz.up123.springboot.common;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @ClassName: DruidDataSource
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/16 22:41
 **/
@Configuration
@PropertySource(value = "application.properties")
public class DruidDataSource1 {
    @Value("${spring.datasource.primary.driver-class}")
    private String driverClass;
    @Value("${spring.datasource.primary.dbType}")
    private String dbType;
    @Value("${spring.datasource.primary.username}")
    private String username;
    @Value("${spring.datasource.primary.password}")
    private String password;
    @Value("${spring.datasource.primary.jdbc-url}")
    private String jdbcUrl;

    @Value("${spring.datasource.primary.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.primary.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.primary.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.primary.maxWait}")
    private long maxWait;

    @Value("${spring.datasource.primary.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.primary.minEvictableIdleTimeMillis}")
    private long minEvictableIdleTimeMillis;

    @Value("${spring.datasource.primary.filters}")
    private String filters;

    //因为SpringBoot也为我们提供了一个默认的datasource，可谓贴心，当我们需要自己的连接池的时候，可以使用@primary注解，告诉Springboot
    // 优先使用我们自己的DataSource
    @Primary
    @Bean(name="dataSource1")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(jdbcUrl);
        dataSource.setDbType(dbType);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWait(maxWait);
        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        return dataSource;
    }

}
