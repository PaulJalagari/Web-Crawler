package com.java.crawler.download;

import java.io.IOException;

public class HttpDownloader {
	
	public void download(String url) {
		String fileURL = url;
		String saveDir = "C:\\Users\\jalagarip\\Downloads\\Down";
		try {
			HttpDownloadUtility.downloadFile(fileURL, saveDir);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
