package org.globaroman.petshopba.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.service.UploadImageService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UploadImageServiceImpl implements UploadImageService {

    @Override
    public InputStream  downloadImage(String imageUrl) {
        URL url = null;
        try {
            url = new URL(imageUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
