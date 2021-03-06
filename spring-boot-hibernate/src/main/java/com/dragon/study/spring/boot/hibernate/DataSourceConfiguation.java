package com.dragon.study.spring.boot.hibernate;

import com.alibaba.druid.pool.DruidDataSource;
import com.dragon.study.spring.boot.hibernate.config.JdbcPoolConfig;
import com.dragon.study.spring.boot.hibernate.config.MysqlProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by dragon on 16/7/26.
 */
@Configuration
@EnableConfigurationProperties({JdbcPoolConfig.class, MysqlProperties.class})
@Slf4j
public class DataSourceConfiguation {

  @Autowired
  JdbcPoolConfig jdbcPoolConfig;

  @Autowired
  MysqlProperties mysqlProperties;

  @Bean(name = "hibernateDataSource")
  @Primary
  public DataSource dataSource() {
    String url = mysqlProperties.getUrl();
    log.info("base url " + url.substring(0, url.indexOf("?")));
    DruidDataSource dataSource = new DruidDataSource();

    dataSource.setUrl(url);
    dataSource.setUsername(mysqlProperties.getUserName());
    dataSource.setPassword(mysqlProperties.getPassword());

    dataSource.setInitialSize(jdbcPoolConfig.getInitialSize());
    dataSource.setMinIdle(jdbcPoolConfig.getMinIdle());
    dataSource.setMaxActive(jdbcPoolConfig.getMaxActive());
    dataSource.setMaxWait(jdbcPoolConfig.getMaxWait());
    dataSource.setTimeBetweenEvictionRunsMillis(jdbcPoolConfig.getTimeBetweenEvictionRunsMillis());
    dataSource.setMinEvictableIdleTimeMillis(jdbcPoolConfig.getMinEvictableIdleTimeMillis());
    dataSource.setValidationQuery(jdbcPoolConfig.getValidationQuery());
    dataSource.setTestWhileIdle(jdbcPoolConfig.isTestWhileIdle());
    dataSource.setTestOnBorrow(jdbcPoolConfig.isTestOnBorrow());
    dataSource.setTestOnReturn(jdbcPoolConfig.isTestOnReturn());
    return dataSource;
  }
}
