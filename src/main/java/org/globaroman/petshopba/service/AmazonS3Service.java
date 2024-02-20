package org.globaroman.petshopba.service;

public interface AmazonS3Service {
    String uploadImage(String path, String objectKey);

    String deleteImage(String image);
}
