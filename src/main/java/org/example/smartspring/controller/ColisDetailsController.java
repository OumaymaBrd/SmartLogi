package org.example.smartspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.colis.ColisDetails.ColisDetailsDTO;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;
import org.example.smartspring.service.ColisDetailsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/detailsColis")
@RequiredArgsConstructor
public class ColisDetailsController {
    private final ColisDetailsService service;
    @GetMapping
    public ResponseEntity<?> getAllColisDetails(
            @RequestParam(required = false) PrioriteColis prioriteColis,
            @RequestParam(required = false)StatutColis statutColis,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateCreation,
            @RequestParam(required = false) String ville_destination
            ) {

        List<ColisDetailsDTO>liste=service.getAll(prioriteColis,statutColis,ville_destination,dateCreation);

        if(liste.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Votre Colis est vide");
        }

        return ResponseEntity.ok(liste);



    }
}
