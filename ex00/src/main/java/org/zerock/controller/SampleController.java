package org.zerock.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.SampleDTO;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/sample/*")
public class SampleController {

	@RequestMapping("")
	public void basic() {
		
		log.info("basic................");
	}
	
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		log.info("dto test 출력 ===================> "+dto);
		return "ex01";
	}
	
	@GetMapping("/ex02")
	public String ex02(@RequestParam("who")String name, @RequestParam("age") int age) {
		log.info("dto test 출력 ===================> "+name + age);
		return "ex02";
	}
	
	@GetMapping("/ex02List")
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		log.info("ids: "+ ids);
		return "ex02List";
	}
	
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("ex06...............");
		SampleDTO dto =new SampleDTO();
		dto.setAge(26);
		dto.setName("이지순");
		
		return dto;
	}
	
	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("/exUPload....................");
	}
	
	@PostMapping("/exUploadPost")
	public void exUPloadPost(ArrayList<MultipartFile> files) {
		files.forEach(file-> {
			log.info("-------------------");
			log.info("name: " + file.getOriginalFilename());
			log.info("size: "+ file.getSize());
		});
	}
	
}
