package org.zerock.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = {"org.zerock.service", "org.zerock.task"})
@MapperScan(basePackages = {"org.zerock.mapper"})
@EnableScheduling
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class RootConfig {
	
	
	@Bean
	public DataSource dataSource() {
		
		HikariConfig hikariConfig = new HikariConfig();
//		hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
//		hikariConfig.setJdbcUrl("jdbc:mysql://localhost/board");
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:mysql://localhost:3306/board?serverTimezone=UTC");
		hikariConfig.setUsername("root");
		hikariConfig.setPassword("1234");
		
		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
		
		return dataSource;
	}
	
	/* mybatis의 핵심 객체  SqlSessionFactory, SQLSession*/
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		return (SqlSessionFactory)sqlSessionFactory.getObject();
	}
	
	  @Bean
	  public DataSourceTransactionManager txManager() {
	      return new DataSourceTransactionManager(dataSource());
	  }


}
