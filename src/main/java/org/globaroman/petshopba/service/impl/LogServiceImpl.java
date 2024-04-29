package org.globaroman.petshopba.service.impl;

import lombok.*;
import lombok.extern.log4j.*;
import org.globaroman.petshopba.service.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.util.concurrent.*;

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
