package org.example.smartspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.example.smartspring.dto.CllientExpediteur.ClientExpediteurDTO;
import org.example.smartspring.dto.CllientExpediteur.UpdateClientExpediteurDTO;
import org.example.smartspring.entities.ClientExpediteur;
import org.example.smartspring.mapper.ClientExpediteurMapper;
import org.example.smartspring.repository.ClientExpediteurRepository;
import org.example.smartspring.services.ClientExpediteurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/clientExpe")
@RequiredArgsConstructor
public class ClientExpediteurController {
    private final ClientExpediteurMapper clientExpediteurMapper;
    private final ClientExpediteurService clientExpediteurService;

    @PostMapping
    public ResponseEntity<ClientExpediteurDTO> CreateclientExpediteur
            (@Valid @RequestBody ClientExpediteurDTO dto)

        {
            ClientExpediteurDTO saved=clientExpediteurService.save(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientExpediteurById(@PathVariable("id") String id) {
        try {
            ClientExpediteurDTO dto = clientExpediteurService.get(id);

            if (dto == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Client non trouvé avec l'ID : " + id);
            }

            return ResponseEntity.ok(dto);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Client non trouvé avec l'ID : " + id);
        }
    }

    @PutMapping
    public ResponseEntity<ClientExpediteurDTO> update(@Valid @RequestBody UpdateClientExpediteurDTO dto) {

        ClientExpediteurDTO updated = clientExpediteurService.update(dto);

        return ResponseEntity.ok(updated);
    }


}
