package org.globaroman.petshopba.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.exception.DataProcessingException;
import org.globaroman.petshopba.service.S3Uploader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class S3UploaderImpl implements S3Uploader {

    @Value("${AWS_ACCESS_KEY}")
    private String accessKey;

    @Value("${AWS_SECRET_KEY}")
    private String secretKey;

    @Value("${AWS_BUCKET}")
    private String bucketName;

    @Override
    public String getPublicUrl(String path, String objectKey) {
        System.out.println(accessKey + " " + secretKey + " " + bucketName);
        try {
            AmazonS3 s3Client = getS3Client();

            PutObjectRequest request = new PutObjectRequest(bucketName, objectKey, new File(path));
            s3Client.putObject(request);

            s3Client.setObjectAcl(bucketName, objectKey, CannedAccessControlList.PublicRead);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            return url.toString();

        } catch (SdkClientException e) {
            throw new DataProcessingException("Can not load image: " + path + " to S3", e);
        }
    }

    private AmazonS3 getS3Client() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

}
