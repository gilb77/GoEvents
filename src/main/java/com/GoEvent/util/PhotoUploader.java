package com.GoEvent.util;

import com.uploadcare.api.Client;
import com.uploadcare.upload.FileUploader;
import com.uploadcare.upload.UploadFailureException;
import com.uploadcare.upload.Uploader;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PhotoUploader {

    private static Client clientPhoto;
    @Autowired
    public void setClientPhoto(Client clientPhoto){ this.clientPhoto = clientPhoto;}

    public static void upload(File sourceFile){
        Uploader uploader = new FileUploader(clientPhoto, sourceFile);
        try {
            com.uploadcare.api.File file = uploader.upload().save();
            System.out.println(file.getOriginalFileUrl());
        } catch (UploadFailureException e) {
            System.out.println("Upload failed :(");
        }
    }




}
