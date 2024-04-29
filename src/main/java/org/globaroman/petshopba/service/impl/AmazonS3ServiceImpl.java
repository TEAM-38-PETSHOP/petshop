package org.globaroman.petshopba.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.*;
import org.globaroman.petshopba.exception.DataProcessingException;
import org.globaroman.petshopba.service.AmazonS3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class AmazonS3ServiceImpl implements AmazonS3Service {

    @Value("${AWS_ACCESS_KEY}")
    private String accessKey;

    @Value("${AWS_SECRET_KEY}")
    private String secretKey;

    @Value("${AWS_BUCKET}")
    private String bucketName;

    @Override
    public InputStream downloadS3(String path) {
        AmazonS3 s3Client = getS3Client();

        S3Object s3ClientObject = s3Client.getObject(bucketName, path);

        try {
            return s3ClientObject.getObjectContent();
        } catch (Exception e) {
            log.error("Can not download file" + path, e);
            throw new DataProcessingException("Can not download file" + path, e);
        }
    }

    @Override
    public String uploadImageUrl(InputStream inputStream, String objectKey) {
        try {
            AmazonS3 s3Client = getS3Client();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(Headers.CONTENT_TYPE);

            PutObjectRequest request = new PutObjectRequest(
                    bucketName,
                    objectKey,
                    inputStream,
                    metadata);

            s3Client.putObject(request);

            s3Client.setObjectAcl(bucketName, objectKey, CannedAccessControlList.PublicReadWrite);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            return "https://" + url.getHost() + url.getPath();

        } catch (SdkClientException e) {
            log.error("Can not load image to S3", e);
            throw new DataProcessingException("Can not load image to S3", e);
        }
    }

    @Override
    public String uploadImage(String path, String objectKey) {
        try {
            AmazonS3 s3Client = getS3Client();

            PutObjectRequest request = new PutObjectRequest(bucketName, objectKey, new File(path));
            s3Client.putObject(request);

            s3Client.setObjectAcl(bucketName, objectKey, CannedAccessControlList.PublicReadWrite);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            return "https://" + url.getHost() + url.getPath();

        } catch (SdkClientException e) {
            log.error("Can not load image: " + path + " to S3", e);
            throw new DataProcessingException("Can not load image: " + path + " to S3", e);
        }
    }

    @Override
    public void uploadDoc(String path, String objectKey) {
        try {
            AmazonS3 s3Client = getS3Client();

            PutObjectRequest request = new PutObjectRequest(bucketName, objectKey, new File(path));
            s3Client.putObject(request);

            s3Client.setObjectAcl(bucketName, objectKey, CannedAccessControlList.PublicReadWrite);



        } catch (SdkClientException e) {
            log.error("Can not load doc: " + path + " to S3", e);
            throw new DataProcessingException("Can not load doc: " + path + " to S3", e);
        }
    }
    public String deleteImage(String url) {

        String[] keyImage = url.split("/");

        try {
            AmazonS3 s3Client = getS3Client();
            DeleteObjectRequest request = new DeleteObjectRequest(bucketName, keyImage[3]);
            s3Client.deleteObject(request);
            return "Image " + keyImage[3] + "was deleting successful";
        } catch (AmazonS3Exception e) {
            log.error("Can not delete image: " + url + " from S3", e);
            throw new DataProcessingException(
                    "Can not delete image: " + url + " from S3", e);
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
