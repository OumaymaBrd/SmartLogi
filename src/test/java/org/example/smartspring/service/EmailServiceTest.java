package org.example.smartspring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> messageCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void sendSimpleEmailAndLog_shouldSendEmail_whenInputIsValid() {

        String to = "test@gmail.com";
        String subject = "Sujet Test";
        String body = "Message Test";
        String colisId = "12345";
        String statut = "EN_STOCK";

        emailService.sendSimpleEmailAndLog(to, subject, body, colisId, statut);

        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage capturedMessage = messageCaptor.getValue();
        assert capturedMessage.getTo()[0].equals(to);
        assert capturedMessage.getSubject().equals(subject);
        assert capturedMessage.getText().equals(body);
    }


    @Test
    void sendSimpleEmailAndLog_shouldNotSendEmail_whenToIsNull() {

        emailService.sendSimpleEmailAndLog(null, "Sujet", "Body", "123", "LIVRE");

        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendSimpleEmailAndLog_shouldNotSendEmail_whenToIsEmpty() {

        emailService.sendSimpleEmailAndLog("", "Sujet", "Body", "123", "LIVRE");

        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }


    @Test
    void sendSimpleEmailAndLog_shouldLogError_whenMailSendingFails() {

        String to = "client@gmail.com";

        doThrow(new RuntimeException("SMTP Error")).when(mailSender)
                .send(any(SimpleMailMessage.class));

        emailService.sendSimpleEmailAndLog(to, "Sujet", "Body", "COLIS1", "TRAITER");

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));

    }
}
