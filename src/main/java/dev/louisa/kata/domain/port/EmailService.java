package dev.louisa.kata.domain.port;

public interface EmailService {
    void openSession(String email);
    void sendToServer(String content);
}
