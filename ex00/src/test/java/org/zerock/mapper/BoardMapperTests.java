package org.zerock.mapper;

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
public class BoardMapperTests {
	
	@Autowired
	private BoardMapper mapper;
	
	
	public void testGetList() {
		mapper.getList().forEach(board ->log.info(board));
	}
	
	@Test
	public void testInsert() {
		
		BoardVO board = new BoardVO();
		board.setTitle("오늘은 금요일 키키");
		board.setContent("칼퇴할거얌~");
		board.setWriter("이지순");
		
//		mapper.insert(board);
		mapper.insertSelectKey(board);
		log.info(board);
	}
	
	

}
