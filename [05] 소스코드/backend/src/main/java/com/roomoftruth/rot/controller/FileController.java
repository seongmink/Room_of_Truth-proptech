package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.service.FileUploadService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class FileController {

	public final FileUploadService fileUploadService;

	@PostMapping("/upload")
	@ApiOperation("파일 업로드")
	public String imageUpload(@RequestParam MultipartFile image, @RequestParam int flag, @RequestParam int num) throws Exception {
		log.info("FileController : imageUpload");

		return fileUploadService.fileUpload(image, flag, num);
	}

	@PostMapping("/uploadNull")
	@ApiOperation("파일 업로드(파일이 없을 경우)")
	public String imageUpload(@RequestParam String image, @RequestParam int flag, @RequestParam int num) throws Exception {
		log.info("FileController : imageUpload");
		MultipartFile multipartFile = null;

		return fileUploadService.fileUpload(multipartFile, flag, num);
	}

}

