package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	
	private int startPage; //20, 30
	private int endPage; //29, 39
	private boolean prev, next; //이전, 다음 버튼이 필요한지 여부
	
	private int total; //전체데이터 수 
	private Criteria cri;
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
		this.startPage = this.endPage -9;
		
		//amount = 페이지당 게시글 수
		//ceil(36.0/5) => 7.2 => 무조건 올림 =>  총 8페이지
		int realEnd = (int) (Math.ceil( (total * 1.0) / cri.getAmount()));
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
	

}
