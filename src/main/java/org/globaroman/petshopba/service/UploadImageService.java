package org.globaroman.petshopba.service;

import java.io.InputStream;

public interface UploadImageService {
    InputStream downloadImage(String imageUrl);
}
