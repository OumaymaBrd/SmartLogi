package org.example.smartspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.colis.UpdateColisDTO;
import org.example.smartspring.dto.livreur.*;
import org.example.smartspring.entities.Colis;
import org.example.smartspring.service.ColisService;
import org.example.smartspring.service.EmailService;
import org.example.smartspring.service.LivreurService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/livreurs")
@RequiredArgsConstructor
public class LivreurController {

    private final ColisService service;

    @GetMapping("/colis/livreur/{id}")
    public List<ConsulterColisAffecterDTO> getColisAffectes(@PathVariable String id) {
        return service.getColisByLivreurId(id);
    }

    private final EmailService emailService;

    // Endpoint pour tester l'envoi d'email
    @GetMapping("/email")
    public ResponseEntity<String> sendTestEmail(@RequestParam String to) {
        String subject = "Test Email";
        String body = "Bonjour, ceci est un test d'envoi d'email depuis Spring Boot.";
        emailService.sendSimpleEmailAndLog(to, subject, body, "TEST", "TEST");
        return ResponseEntity.ok("Email de test envoyé à " + to);
    }


    @PutMapping("/{colisId}")
    public ResponseEntity<String> updateColis(@PathVariable String colisId,
                                              @RequestBody UpdateColisDTO dto) {
        Colis updatedColis = service.updateColis(dto, colisId);

        String message = "Le colis " + colisId + " a été mis à jour avec succès";

        if (dto.getStatut() != null) {
            message += " | Statut : " + updatedColis.getStatut();
        }

        if (dto.getLivreurId() != null) {
            message += " | Livreur collecteur affecté : " + dto.getLivreurId();
        }

        if (dto.getLivreur_id_livree() != null) {
            message += " | Livreur livré affecté : " + dto.getLivreur_id_livree();
        }

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
