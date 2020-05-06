package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	
	@Select("select * from tbboard where bno>0")
	public List<BoardVO> getList();
	
	@Select("select * from tbboard where bno = #{bno}") 
	public BoardVO read(int bno);
	
	public void insert(BoardVO board); 
	public void insertSelectKey(BoardVO board);
	
	public int update(BoardVO board); 
	
	public int delete(int bno);
	
	 
	public List<BoardVO> getListWithPaging(Criteria cri);
	 
	public int getTotalCount(Criteria cri); //파라미터 안줘도 되지만 검색에서 필요
	
	public void updateReplyCnt(@Param("bno") int bno, @Param("amount") int amount);

}
