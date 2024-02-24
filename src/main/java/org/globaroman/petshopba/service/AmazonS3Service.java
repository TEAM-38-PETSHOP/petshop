package org.globaroman.petshopba.service;

import java.io.InputStream;

public interface AmazonS3Service {
    String uploadImage(String path, String objectKey);

    String uploadImageUrl(InputStream inputStream, String objectKey);

    String deleteImage(String image);

    InputStream downloadS3(String path);
}
