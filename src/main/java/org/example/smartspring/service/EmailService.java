package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private static final Logger emailLogger = LoggerFactory.getLogger(EmailService.class);

    public void sendSimpleEmailAndLog(String to, String subject, String body, String colisId, String statut) {
        if (to == null || to.isEmpty()) return; // si l'email est null, on ne fait rien

        // Création du message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        try {
            // Envoi email
            mailSender.send(message);

            // Log dans le logger spécifique email
            emailLogger.debug(" Email envoyé à {} | Colis={} | Statut={} | Sujet='{}'", to, colisId, statut, subject);

        } catch (Exception e) {
            // Log d'erreur
            emailLogger.error(" Erreur en envoyant l'email à {} | Colis={} | Statut={} | Sujet='{}' | Erreur={}",
                    to, colisId, statut, subject, e.getMessage(), e);
        }
    }
}
