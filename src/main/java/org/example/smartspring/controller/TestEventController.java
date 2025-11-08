package org.example.smartspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.events.ColisStatusChangeEvent;
import org.example.smartspring.service.EmailService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestEventController {

    private final ApplicationEventPublisher eventPublisher;
    private final EmailService emailService;

    // tester l'événement
    @GetMapping("/email/{colisId}/{statut}")
    public String testEmail(@PathVariable String colisId, @PathVariable String statut) {
        eventPublisher.publishEvent(new ColisStatusChangeEvent(colisId, statut));
        return " Event envoyé pour colis = " + colisId + " avec statut = " + statut;
    }


}
