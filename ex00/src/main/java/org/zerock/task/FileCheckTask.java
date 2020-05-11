package org.zerock.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zerock.domain.BoardAttachVO;
import org.zerock.mapper.BoardAttachMapper;

import lombok.extern.log4j.Log4j;

/*
 * DB의 어제 사용된 파일 목록을 가져오고
 * 해당 폴더의 파일 목록에서 DB에 없는 파일 찾아내기
 * 그 파일들은 삭제처리
 */
@Log4j
@Component
public class FileCheckTask {
	
	@Autowired
	private BoardAttachMapper attachMapper;
	
	
	private String getFolderYesterDay() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -1);
		
		String str = sdf.format(cal.getTime());
		//File.separator = \
		return str.replace("-", File.separator);
	}
	
//	@Scheduled(cron="0 * * * * *")
	//매일 새벽 2시에 동작
	@Scheduled(cron = "0 0 2 * * *")
	public void checkFiles() throws Exception{
		
		log.warn("File Check Task run...........................");
		log.warn(new Date());
		
		//file List in DB
		List<BoardAttachVO> fileList = attachMapper.getOldFiles();
		
		//ready for check file in directory with DB file list
		List<Path> fileListPaths = fileList.stream()
				.map(vo -> Paths.get("C:\\upload", vo.getUploadPath(), vo.getUuid()+ "_" + vo.getFileName()))
				.collect(Collectors.toList());
		
		//이미지파일은 썸네일 목록도 필요
		fileList.stream().filter(vo->vo.isFileType() == true)
			.map(vo->Paths.get("C:\\upload", vo.getUploadPath(), "s_" +  vo.getUuid()+ "_" + vo.getFileName()))
			.forEach(p -> fileListPaths.add(p));
		
		log.warn("=================================================");
		
		fileListPaths.forEach(p -> log.warn(p));
		
		
		//files in yesterday directory
		File targetDir =  Paths.get("C:\\upload", getFolderYesterDay()).toFile();
		
		File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);
		
		log.warn("--------------------------------------------------");
		for(File file: removeFiles) {
			log.warn(file.getAbsolutePath());
			file.delete();
		}
	}
	
	//주기 제어 어노테이션
	//0초가 될때마다 실행한다는 것 (초,분,시간,일,월,주, 연도)
//	@Scheduled(cron="0 * * * * *")
//	public void checkFiles() throws Exception{
//		log.warn("File Check Task run.....................");
//		log.warn("=========================================");
//	}
	
	
	

}
