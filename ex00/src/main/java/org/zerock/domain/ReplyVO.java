package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {
	
	private int rno;
	private int bno; //외래키
	
	private String reply;
	private String replyer;
	private Date replyDate;
	private Date updateDate;
	

}
