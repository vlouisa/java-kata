package dev.louisa.kata.infrastructure.adapter;

import dev.louisa.kata.domain.port.EmailService;

public class ConsoleEmailService implements EmailService {
    @Override
    public void openSession(String email) {
        System.out.println("Sending email to %s...".formatted(email));
    }

    @Override
    public void sendToServer(String content) {
        System.out.println(content);
    }
}
