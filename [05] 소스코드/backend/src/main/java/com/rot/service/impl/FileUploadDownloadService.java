package com.rot.service.impl;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rot.exception.FileDownloadException;
import com.rot.exception.FileUploadException;
import com.rot.model.Agent;
import com.rot.property.FileUploadProperties;
import com.rot.service.IAgentService;
import com.rot.service.IBuildingService;

@Service
public class FileUploadDownloadService {
	
	@Autowired
	private IBuildingService buildingService;
	
	@Autowired
	private IAgentService agentService;
	
	private final Path fileLocation;

	/*
	 * 다음으로 파일이 저장될 디렉토리를 설정하고 디렉토리를 생성하는 소스를 추가한다. Service가 실행될때 생성자에서 기존에 생성한
	 * 설정클래스인 FileUploadProperties 클래스로 기본 디렉토리를 설정하고 생성한다.
	 */
	@Autowired
	public FileUploadDownloadService(FileUploadProperties prop) {
		this.fileLocation = Paths.get(prop.getUploadDir()).toAbsolutePath().normalize();
		// logger.info(this.fileLocation.toString());
		try {
			Files.createDirectories(this.fileLocation); // 일반 이미지 디렉토리
		} catch (Exception e) {
			throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
		}
	}

	// 파일을 저장
	public String storeFile(MultipartFile file, String requestType, long num, int flag) {
		
		String fileName = null;
		
		if(flag == 0) { // 거래
			fileName = buildingService.getBuildingByNum(num).getImage();
		}  else { // 공인중개사 사진
			fileName = newFileName(file.getOriginalFilename()); // agent 이미지 변경 쿼리 
			fileName = fileName.replace(" ", "_");
			Agent agent = new Agent();
			agent.setNum(num);
			agent.setPicture(fileName);
			agentService.updateImage(agent);
		}
		
		try {
			// 파일명에 부적합 문자가 있는지 확인한다.
			if (fileName.contains(".."))
				throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);

			Path targetLocation = this.fileLocation.resolve(fileName);

			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (Exception e) {
			throw new FileUploadException("[" + fileName + "] 파일 업로드에 실패하였습니다. 다시 시도하십시오.", e);
		}
	}

	public static String newFileName(String fileName) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String format_time1 = format1.format(System.currentTimeMillis());
		return format_time1 + "_" + fileName;
	}

	// 파일 다운로드
	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) {
				return resource;
			} else {
				throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
			}
		} catch (MalformedURLException e) {
			throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
		}
	}

	

}
