package org.zerock.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {org.zerock.config.RootConfig.class})
public class BoardServiceTests {

	@Autowired
	private BoardService service;
	
	@Test
	public void testRegister() {
		BoardVO board = new BoardVO();
		
		board.setTitle("dfdfD");
		board.setContent("aaa");
		board.setWriter("bbb");
		service.register(board);
		
		log.info(board.getBno());
	}
	
	
	
	public void testExist() {
		log.info(service);
		assertNotNull(service);
	}
	
	
	
}
