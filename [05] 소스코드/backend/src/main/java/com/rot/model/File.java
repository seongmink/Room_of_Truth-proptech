package com.rot.model;

import lombok.Data;

@Data
public class File {
	private long num;
	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private long size;


}
