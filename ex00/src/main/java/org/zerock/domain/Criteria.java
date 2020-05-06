package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

//검색의 기준, 페이지 번호와 게시물 수를  전달
@Getter
@ToString
public class Criteria {

	private int pageNum; // 현재 페이지 번호
	private int amount; // 보여줄 게시글 개수
	private int dbPageNum; //게시물 시작번호 , 1페이지면 0부터, 2페이지면 10부터

	private String type;
	private String keyword;

	//최초로 목록에 들어왔을 경우를 위해서 기본 셋팅을 하기 위한 생성자
	public Criteria() {
		this(1, 10);
		this.dbPageNum = (this.pageNum * 10) - 10;
	}

	public Criteria(int pageNum, int amount) {
		if (pageNum == -1) {
			pageNum = 1;
		}
		this.pageNum = pageNum;
		this.amount = amount;
		this.dbPageNum = (this.pageNum * 10) - 10;
	}

	public String[] getTypeArr() {

		return type == null ? new String[] {} : type.split("");
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		this.dbPageNum = (this.pageNum * 10) - 10;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setDbPageNum(int dbPageNum) {
		this.dbPageNum = dbPageNum;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}