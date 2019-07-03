package xyz.up123.springboot;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.ContextLoader;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ershixiong on 2018/9/19.
 */
@EnableTransactionManagement
@SpringBootApplication
@MapperScan("xyz.up123.springboot.dao")//必须加这个，不加报错，如果不加，也可以在每个mapper上添加@Mapper注释
public class Boot {
    public static void main(String[] args) {

        SpringApplication.run(Boot.class,args);
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource druid() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    //注册后台界面
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        HashMap<String, String> param = new HashMap<>();
        param.put("loginUsername", "admin");
        param.put("loginPassword", "111111");
        param.put("allow", "");
        param.put("deny", "33.31.3.3");
        bean.setInitParameters(param);
        return bean;
    }

    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        bean.addUrlPatterns("/*");
        Map<String, String> param = new HashMap<>();
        param.put("exclusions", ".jpg,/druid/*");

        bean.setInitParameters(param);
        return bean;
    }
}





