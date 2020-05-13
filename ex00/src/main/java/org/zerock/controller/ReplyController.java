package org.zerock.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/replies")
@RestController
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ReplyController {

	private ReplyService service;

	// 댓글 등록
	@PostMapping(value = "/new", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
		// @RequestBody를 통해서 json 데이터를 vo로 변환
		// ResponseEntity는 데이터뿐만 아닌 http상태코드 및 응답메시지를 같이 전달

		log.info("ReplyVO: " + vo);
		int insertCount = service.register(vo);
		log.info("Reply insert count: " + insertCount);
		// 200은 성공 500은 에러
		return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 특정 게시물의 댓글 확인
//	@GetMapping(value = "/pages/{bno}/{page}", produces = { MediaType.APPLICATION_ATOM_XML_VALUE,
//			MediaType.APPLICATION_JSON_UTF8_VALUE })
//	public ResponseEntity<List<ReplyVO>> getList(@PathVariable("page") int page, @PathVariable("bno") int bno) {
//		log.info("getList.........");
//		Criteria cri = new Criteria(page, 10);
//		log.info(cri);
//
//		return new ResponseEntity<>(service.getList(cri, bno), HttpStatus.OK);
//	}

	
	//특정 게시물의 댓글 확인
	@GetMapping(value = "/pages/{bno}/{page}", 
			produces = {MediaType.APPLICATION_ATOM_XML_VALUE,
						MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable("page") int page,
			@PathVariable("bno") int bno){
		
		log.info("getList.........");
		Criteria cri = new Criteria(page, 10);
		log.info("get reply list bno: "+ bno);
//		log.info("cri " +cri);
		
		return new ResponseEntity<>(service.getListPage(cri, bno), HttpStatus.OK);
	}

	
	// 댓글 삭제 및 조회
	@GetMapping(value = "/{rno}", produces = { MediaType.APPLICATION_ATOM_XML_VALUE,
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") int rno) {

		log.info("get: " + rno);
		return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	//@RequestBody 는 JSON정보를 받을 때 사용
	//@ResponseBody는 JSON정보를 전달할 때 사용, @RestController 안에 들어있으므로 굳이 사용 X
	
	@PreAuthorize("principal.username == #vo.replyer")
	@DeleteMapping("/{rno}")
	public ResponseEntity<String> remove(@RequestBody ReplyVO vo, @PathVariable("rno") int rno) {

		log.info("remove: " + rno);
		log.info("replyer: " + vo.getReplyer());

		return service.remove(rno) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}
	//consumes 데이터 받는 타입, produces 데이터 응답 타입
//	/consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE }
	// 댓글 수정
	@PreAuthorize("authentication.principal.username == #vo.replyer") //이게 작동을 안함
	@RequestMapping(method = { RequestMethod.PUT,
			RequestMethod.PATCH }, value = "/{rno}", consumes = "application/json")
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable("rno") int rno, Principal principal){
		
		log.info("-----------------------------------");
		log.info(principal.getName());
		log.info("rno: " + rno + "modify: " + vo);

		return service.modify(vo) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
