package org.globaroman.petshopba.service;

import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {

    String uploadImageUrl(InputStream inputStream, String objectKey);

    String deleteImage(String image);

    InputStream downloadS3(String path);

    String uploadImageFromFile(MultipartFile file, String objectKey);

    void uploadDoc(String path, String objectKey);
}
