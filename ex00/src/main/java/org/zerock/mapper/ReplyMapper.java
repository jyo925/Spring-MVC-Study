package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyMapper {
	
	//등록 create
	public int insert(ReplyVO vo);

	//조회 read
	public ReplyVO read(int rno);
	
	//삭제 delete
	public int delete(int rno);
	
	//수정 update
	public int update(ReplyVO reply);
	
	public List<ReplyVO> getListWithPaging(
			@Param("cri") Criteria cri,
			@Param("bno") int bno);
	
	//게시물의 댓글 수
	public int getCountByBno(int bno);
}
