package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.service.FileUploadService;
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
	@ResponseBody
	public String imageUpload(@RequestParam MultipartFile image, @RequestParam int flag, int num) throws Exception {
		log.info("FileController : imageUpload");

		if(!image.getContentType().contains("image")) {
			return "failed";
		}

		return fileUploadService.fileUpload(image, flag, num);
	}

}

