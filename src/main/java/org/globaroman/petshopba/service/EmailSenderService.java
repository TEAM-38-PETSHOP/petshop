package org.globaroman.petshopba.service;

public interface EmailSenderService {
    String sendEmail(String from, String to, String subject, String body);
}
