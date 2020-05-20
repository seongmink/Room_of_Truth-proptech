package com.rot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rot.model.FileUploadResponse;
import com.rot.service.impl.FileUploadDownloadService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileUploadDownloadService fileService;

	@PostMapping("/uploadfile") // 파일 저장
	@ApiOperation("파일 업로드")
	public String uploadFile(@RequestParam(required = false) MultipartFile file, long num, int flag) {
	    logger.info("POST : /api/uploadfile");
	    FileUploadResponse newFile = new FileUploadResponse();
	    String fileName = "";
	    
	    if (file == null) {
	        fileName = "default.png";
	        newFile.setFileName(fileName);
	        newFile.setFileType("image/png");
	        newFile.setSize(25125);
	    } else {
	    	fileName = fileName.replace(" ", "_");
	        newFile.setFileName(fileName);
	        newFile.setSize(file.getSize());
	        fileName = fileService.storeFile(file, "images", num, flag); // 파일 저장 
	    }

	    newFile.setNum(num);
	    // 저장한 파일 다운로드 URI 생성
	    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
	            .path(fileName).toUriString();
	    
	    newFile.setFileDownloadUri(fileDownloadUri);

	    return "success";
	}

//	@PostMapping("/image")
//	@ApiOperation("이미지 보내기")
//	public List<String> getImage(@RequestBody Building[] building) {
//		logger.info("POST : /api/image");
//
//		List<String> result = new ArrayList<>();
//
//		for (int i = 0; i < building.length; i++) {
//			long num = buildingService.getRecentNum(building[i]);
//			String name = fileService.getFileName(num);
//			result.add("images/" + name);
//		}
//
//		return result;
//	}
//
//  @PostMapping("/uploadMultipleFiles")
//  public List<FileUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
//      return Arrays.asList(files)
//              .stream()
//              .map(file -> uploadFile(file))
//              .collect(Collectors.toList());
//  }
//	
//	
//
//	@GetMapping("/downloadFile/{fileName:.+}")
//	@ApiOperation("파일 다운로드")
//    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
//         // Load file as Resource
//		Resource resource = fileService.loadFileAsResource(fileName);
//
//		// Try to determine file's content type
//		String contentType = null;
//
//		try {
//			// 파일 타입 추출
//			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//		} catch (IOException e) {
//			logger.info("Could not determine file type.");
//		}
//
//		// Fallback to the default content type if type could not be determined
//		if (contentType == null) {
//			contentType = "application/octet-stream";
//		}
//		// response
//		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//				.body(resource);
//	}

}
