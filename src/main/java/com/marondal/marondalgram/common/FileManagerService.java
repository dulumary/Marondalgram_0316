package com.marondal.marondalgram.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


public class FileManagerService {
	
	public static final String FILE_UPLOAD_PATH = "D:\\김인규쌤\\web_1116\\springProject\\upload\\marondalgram\\images";
	
	private static Logger logger = LoggerFactory.getLogger(FileManagerService.class);
	
	public static String saveFile(int userId, MultipartFile file) {
		
		if(file == null) {
			return null;
		}
		
		// 사용자 별로 폴더를 구분
		// 시간을 포함해서 폴더를 구분
		// UNIX TIME : 1970년 1월 1일 부터 흐른 시간을 (milli second 1/1000)
		// 폴더 이름 : userId_time (3_3949828284)
		
		String directoryName = "/" + userId + "_" + System.currentTimeMillis() + "/";
		
		// 디렉토리 생성
		String directoryPath = FILE_UPLOAD_PATH + directoryName;
		File directory = new File(directoryPath);
		if(!directory.mkdir()) {
			// 디렉토리 생성 실패
			return null;
		}
		
		// 파일 저장
		try {
			byte[] bytes = file.getBytes();
			
			String filePath = directoryPath + file.getOriginalFilename();
			Path path = Paths.get(filePath);
			
			Files.write(path, bytes);
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			return null;
		}
		
		// 클라이언트에서 저장된 파일을 접근할 수 있는 경로를 리턴
		// 경로 규칙 /images/2_39i980139/test.png
		// http://localhost:8080/images/2_39i980139/test.png
		
		return "/images" + directoryName + file.getOriginalFilename();
		
	}
	
	// 파일 삭제 기능
	public static boolean removeFile(String filePath) { // /images/2_1679477699709/blackbird-g9b42bb68b_640.jpg
		
		if(filePath == null) {
			logger.info("삭제 대상 파일 없음");
			return false;
		}
		// 실제 파일 저장 경로 찾기 
		// /images 를 제거하고, 나머지 부분을 FILE_UPLOAD_PATH에 이어 붙인다. 
		// D:\\김인규쌤\\web_1116\\springProject\\upload\\memo\\image/2_1679477699709/blackbird-g9b42bb68b_640.jpg
		
		String fullFilePath = FILE_UPLOAD_PATH + filePath.replace("/images", "");
		Path path = Paths.get(fullFilePath);
		
		// 파일이 존재하는지 
		if(Files.exists(path)) {
			
			try {
				Files.delete(path);
			} catch (IOException e) {
				
				logger.error("removeFile : 파일 삭제 에러 " + fullFilePath);
				e.printStackTrace();
				return false;
			}
		}
		
		// 디렉토리 제거
		Path dirPath = path.getParent();
		
		if(Files.exists(dirPath)) {
			try {
				Files.delete(dirPath);
			} catch (IOException e) {
				logger.error("removeFile : 디렉토리 삭제 에러 " + fullFilePath);
				e.printStackTrace();
				return false;
			}
			
		}
		
		return true;
		
	}

}
