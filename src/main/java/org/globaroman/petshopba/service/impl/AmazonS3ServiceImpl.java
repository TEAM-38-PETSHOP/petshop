package org.globaroman.petshopba.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.exception.DataProcessingException;
import org.globaroman.petshopba.service.AmazonS3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AmazonS3ServiceImpl implements AmazonS3Service {

    @Value("${AWS_ACCESS_KEY}")
    private String accessKey;

    @Value("${AWS_SECRET_KEY}")
    private String secretKey;

    @Value("${AWS_BUCKET}")
    private String bucketName;

    @Override
    public String uploadImage(String path, String objectKey) {
        try {
            AmazonS3 s3Client = getS3Client();

            PutObjectRequest request = new PutObjectRequest(bucketName, objectKey, new File(path));
            s3Client.putObject(request);

            s3Client.setObjectAcl(bucketName, objectKey, CannedAccessControlList.PublicRead);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

//            System.out.println("getFile -" + url.getFile() + " getHost- " + url.getHost() + " getPath- " + url.getPath() + " getRef- "+ url.getRef()
//                    + " getQuery- " + url.getQuery() + " toExternalForm- " + url.toExternalForm());

            return url.toString();

        } catch (SdkClientException e) {
            throw new DataProcessingException("Can not load image: " + path + " to S3", e);
        }
    }

    public void deleteImage(String url) {
        String[] splitUrl = url.split("\\?");
        String[] keyImage = splitUrl[0].split("/");
        System.out.println(keyImage[3]);
        System.out.println(bucketName);
        try {
            AmazonS3 s3Client = getS3Client();
            DeleteObjectRequest request = new DeleteObjectRequest(bucketName, keyImage[3]);
            s3Client.deleteObject(request);
        } catch (AmazonS3Exception e) {
            throw new DataProcessingException("Can not delete image: " + splitUrl[0] + " from S3", e);
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
