package org.zerock.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.zerock.security.CustomLoginSuccessHandler;
import org.zerock.security.CustomUserDetailsService;

import lombok.extern.log4j.Log4j;

@Configuration
@EnableWebSecurity // @EnableWebSecurity 는 스프링 MVC와 스프링 시큐리티를 결합
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, proxyTargetClass = true)
@Log4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Bean
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new CustomLoginSuccessHandler();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService customUserService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}

	// xml설정을 대신하는 부분
	@Override
	public void configure(HttpSecurity http) throws Exception {

		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.addFilterBefore(filter, CsrfFilter.class);

		http.authorizeRequests().antMatchers("/sample/all").permitAll().antMatchers("/sample/admin")
				.access("hasRole('ROLE_ADMIN')").antMatchers("/sample/member").access("hasRole('ROLE_MEMBER')")
				.antMatchers("/board/register").authenticated().antMatchers("/deleteFile").authenticated()
				.antMatchers("/uploadAjaxAction").authenticated().antMatchers("/replies/new").authenticated();
//		.antMatchers("/board/list").authenticated();
//				.anyRequest().authenticated()
		/*
		 * .antMatchers("/board/register").access("hasRole('ROLE_MEMBER')")
		 * .antMatchers("/board/register").access("hasRole('ROLE_ADMIN')");
		 */

		http.formLogin().loginPage("/customLogin").loginProcessingUrl("/login").successHandler(loginSuccessHandler());

//		http.formLogin().loginPage("/customLogin").loginProcessingUrl("/login");

		http.logout().logoutUrl("/customLogout").invalidateHttpSession(true).deleteCookies("remember-me",
				"JSESSION_ID");

		http.rememberMe().key("zerock").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(604800);

	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		log.info("configure............................");
//
//		auth.inMemoryAuthentication().withUser("admin")
//		.password("{noop}admin").roles("ADMIN");
//		auth.inMemoryAuthentication().withUser("member")
//		.password("$2a$10$J4IdcucYJwvgUabB6o9AL.QhVcBirQzoRiI4QfRMwVupC3kNaD0si").roles("MEMBER");
//	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		log.info("configure JDBC.................................");

//		String queryUser = "select userid, userpw, enabled from tbmember where userid = ? " ;
//		String queryDetails = "select userid, auth from tbmemberauth where userid = ? ";
//		
//		auth.jdbcAuthentication()
//		.dataSource(dataSource)
//		.passwordEncoder(passwordEncoder())
//		.usersByUsernameQuery(queryUser)
//		.authoritiesByUsernameQuery(queryDetails);

		auth.userDetailsService(customUserService()).passwordEncoder(passwordEncoder());

	}

}
