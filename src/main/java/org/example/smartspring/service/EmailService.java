package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.entities.HistoriqueEmail;
import org.example.smartspring.repository.HistoriqueEmailRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final HistoriqueEmailRepository historiqueEmailRepository;


    @Async
    public void sendSimpleEmailAndLog(String to, String subject, String text, String colisId, String statut) {
        HistoriqueEmail log = HistoriqueEmail.builder()
                .destinataire(to)
                .sujet(subject)
                .contenu(text)
                .dateEnvoi(LocalDateTime.now())
                .colisId(colisId)
                .statutColis(statut)
                .build();

        try {
            if (to == null || to.isBlank()) {
                log.setSuccess(false);
                log.setErreur("Missing recipient");
                historiqueEmailRepository.save(log);
                return;
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);

            log.setSuccess(true);
            historiqueEmailRepository.save(log);
        } catch (Exception ex) {
            log.setSuccess(false);
            log.setErreur(ex.getMessage());
            historiqueEmailRepository.save(log);
        }
    }

    public void testSendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("oumaymabramid@gmail.com");
        message.setSubject("Test Email Spring Boot");
        message.setText("Ceci est un email de test");
        message.setFrom("oumaymabramid@gmail.com");

        mailSender.send(message);
        System.out.println("Email de test envoyé !");
    }
}
