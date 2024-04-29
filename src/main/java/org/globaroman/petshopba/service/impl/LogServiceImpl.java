package org.globaroman.petshopba.service.impl;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.globaroman.petshopba.service.AmazonS3Service;
import org.globaroman.petshopba.service.LogService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class LogServiceImpl implements LogService {

    private final AmazonS3Service amazonS3Service;

    @Scheduled(fixedRate = 60, timeUnit = TimeUnit.MINUTES)
    public void downloadLogToS3() {
        amazonS3Service.uploadDoc("application.log", "logs/app.log");
    }
}
