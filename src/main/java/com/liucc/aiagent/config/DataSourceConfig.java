package com.liucc.aiagent.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 多数据源配置
 */
@Configuration
@EnableConfigurationProperties
public class DataSourceConfig {

    /**
     * mysql作为主数据源
     * 
     * @return
     */
    @Primary
    @Bean(name = "mysqlDataSource")
    @ConfigurationProperties(prefix = "multiple-datasource.mysql")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/ai-agent?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai")
                .username("root")
                .password("123456")
                .build();
    }

    /**
     * postgresql作为向量数据库
     * 
     * @return
     */
    @Bean(name = "postgresqlDataSource")
    @ConfigurationProperties(prefix = "multiple-datasource.postgresql")
    public DataSource postgresqlDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://rm-cn-ce74b52i20001gwo.rwlb.rds.aliyuncs.com/tiga_ai_agent")
                .username("ai_agent123")
                .password("Liu19991212")
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    @Primary
    @Bean(name = "mysqlJdbcTemplate")
    public JdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "postgresqlJdbcTemplate")
    public JdbcTemplate postgresqlJdbcTemplate(@Qualifier("postgresqlDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}