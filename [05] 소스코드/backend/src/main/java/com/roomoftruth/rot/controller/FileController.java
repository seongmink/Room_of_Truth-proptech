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

	/**
	 *
	 * @param image
	 * @param flag
	 * @param num
	 * @return ConvertedImageName
	 * @throws Exception
	 */
	@PostMapping("/upload")
	@ResponseBody
	public String imageUpload(@RequestParam MultipartFile image, @RequestParam int flag, @RequestParam int num) throws Exception {
		log.info("FileController : imageUpload");
		System.out.println(image);
		return fileUploadService.fileUpload(image, flag, num);
	}

	@PostMapping("/uploadNull")
	@ResponseBody
	public String imageUpload(@RequestParam String image, @RequestParam int flag, @RequestParam int num) throws Exception {
		log.info("FileController : imageUpload");
		MultipartFile multipartFile = null;
		System.out.println(image);
		return fileUploadService.fileUpload(multipartFile, flag, num);
	}

}

