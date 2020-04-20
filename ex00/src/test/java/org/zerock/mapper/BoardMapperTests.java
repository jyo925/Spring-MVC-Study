package org.zerock.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

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
	public void testSearch() {
		
		Criteria cri = new Criteria();
		cri.setKeyword("아이스박스");
		cri.setType("C");
		log.info("-----------------"+cri.getTypeArr());
		
		List<BoardVO> list = mapper.getListWithPaging(cri);
		
		list.forEach(board -> log.info(board));
		
		
	}
	
	
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
