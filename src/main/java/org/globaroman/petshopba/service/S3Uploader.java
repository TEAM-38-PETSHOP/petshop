package org.globaroman.petshopba.service;

public interface S3Uploader {
    String getPublicUrl(String path, String objectKey);
}
