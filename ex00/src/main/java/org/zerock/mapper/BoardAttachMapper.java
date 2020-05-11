package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardAttachVO;

public interface BoardAttachMapper {
	
	//첨부파일 등록
	public void insert(BoardAttachVO vo);
	
	//첨부파일 삭제
	public void delete(String uuid);
	
	//해당 게시물의 첨부파일 목록
	public List<BoardAttachVO> findByBno(int bno);
	
	//게시물 삭제시 모든 파일 삭제
	public void deleteAll(int bno);
	
	//어제날짜 첨부파일 목록
	public List<BoardAttachVO> getOldFiles();
	
}
