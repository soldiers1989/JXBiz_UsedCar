package com.etong.android.frame.request;

import org.apache.http.HttpEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

public class MutipartParams {
	private static class FileWrapper {
		public InputStream inputStream;
		public String fileName;
		public String contentType;

		public FileWrapper(InputStream inputStream, String fileName,
				String contentType) {
			this.inputStream = inputStream;
			this.fileName = fileName;
			this.contentType = contentType;
		}

		public String getFileName() {
			if (fileName != null) {
				return fileName;
			} else {
				return "noname";
			}
		}
	}

	protected ConcurrentHashMap<String, FileWrapper> fileParams;

	public MutipartParams() {
		init();
	}

	private void init() {
		// urlParams = new ConcurrentHashMap<String, String>();
		fileParams = new ConcurrentHashMap<String, FileWrapper>();
	}

	/**
	 * 添加文件到request
	 * 
	 * @param key
	 * @param file
	 */
	public void put(String key, File file) {
		try {
			put(key, new FileInputStream(file), file.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加stream到request
	 * 
	 * @param key
	 * @param stream
	 * @param fileName
	 */
	public void put(String key, InputStream stream, String fileName) {
		put(key, stream, fileName, null);
	}

	/**
	 * 添加stream到request
	 * 
	 * @param key
	 * @param stream
	 * @param fileName
	 * @param contentType
	 */
	public void put(String key, InputStream stream, String fileName,
			String contentType) {
		if (key != null && stream != null) {
			fileParams.put(key, new FileWrapper(stream, fileName, contentType));
		}
	}

	public HttpEntity getEntity() {
		HttpEntity entity = null;
		MultipartEntity multipartEntity = new MultipartEntity();
		// Add file params
		int currentIndex = 0;
		int lastIndex = fileParams.entrySet().size() - 1;
		for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams
				.entrySet()) {
			FileWrapper file = entry.getValue();
			if (file.inputStream != null) {
				boolean isLast = currentIndex == lastIndex;
				if (file.contentType != null) {
					multipartEntity.appendFile(entry.getKey(),
							file.getFileName(), file.inputStream,
							file.contentType, isLast);
				} else {
					multipartEntity.appendFile(entry.getKey(),
							file.getFileName(), file.inputStream, isLast);
				}
			}
			currentIndex++;
		}
		entity = multipartEntity;
		return entity;
	}
}
