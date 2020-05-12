package org.zerock.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {org.zerock.config.RootConfig.class, 
		org.zerock.config.SecurityConfig.class})
@Log4j
public class PasswordEncoderTests {

	
	@Autowired
	private PasswordEncoder pwEncoder;
	
	
	@Test
	public void testEncode() {
		
		String str = "admin93";
		
		String enStr = pwEncoder.encode(str);
		
		log.info("-------------------->" + enStr);
		//$2a$10$J4IdcucYJwvgUabB6o9AL.QhVcBirQzoRiI4QfRMwVupC3kNaD0si
	}
}
