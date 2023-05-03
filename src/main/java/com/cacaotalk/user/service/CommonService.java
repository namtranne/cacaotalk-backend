package com.cacaotalk.user.service;

import com.google.api.gax.paging.Page;
import com.google.api.services.storage.model.Bucket;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import javax.servlet.http.Part;

import org.apache.poi.ooxml.util.PackageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class CommonService {
	
	@Autowired
    private Storage firebaseStorage;
	
	public String uploadImage(MultipartFile filePart, String fileName) throws IOException {
		String bucketName = "cacaotalk-36115.appspot.com";
		String contentType = filePart.getContentType();
		String uuid = UUID.randomUUID().toString();
		String blobName = "images/" + uuid + "/" + fileName;
		BlobId blobId = BlobId.of(bucketName, blobName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
		        .setContentType(contentType)
		        .build();
		byte[] imageData = filePart.getBytes();
		Blob blob = firebaseStorage.create(blobInfo, imageData);
		return blob.getMediaLink();
	}
	
	public void deleteFolderAndContents(String folderName) {
	    String bucketName = "cacaotalk-36115.appspot.com"; 
	    Page<Blob> blobsToDelete = firebaseStorage.list(bucketName, Storage.BlobListOption.prefix(folderName));
	    for (Blob blob : blobsToDelete.iterateAll()) {
	        blob.delete();
	    }
	}
	
	public void remove(String path) {
		if(path==null ||path.isEmpty() ) {
			return;
		}
		String folder = path
				.replace("https://storage.googleapis.com/download/storage/v1/b/cacaotalk-36115.appspot.com/o/", "")
				.replaceAll("%2F", "/")
				.replaceAll("[^/]+/$", "");
		deleteFolderAndContents(folder);
	}
}
