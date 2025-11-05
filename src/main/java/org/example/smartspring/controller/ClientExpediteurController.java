package org.example.smartspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.clientexpediteur.AddClientExpediteurDTO;
import org.example.smartspring.dto.clientexpediteur.UpdateClientExpediteurDTO;
import org.example.smartspring.dto.clientexpediteur.ClientExpediteurDTO;
import org.example.smartspring.service.ClientExpediteurService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients-expediteurs")
@RequiredArgsConstructor
public class ClientExpediteurController {

    private final ClientExpediteurService clientExpediteurService;

    @PostMapping
    public ResponseEntity<ClientExpediteurDTO> createClientExpediteur(@Valid @RequestBody AddClientExpediteurDTO dto) {
        return new ResponseEntity<>(clientExpediteurService.createClientExpediteur(dto), HttpStatus.CREATED);
    }

    @GetMapping("/clients-expediteurs")
    public ResponseEntity<Page<ClientExpediteurDTO>> getAllClientsExpediteurs(Pageable pageable) {
        Page<ClientExpediteurDTO> page = clientExpediteurService.getAllClientsExpediteurs(pageable);

        if (page.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(page);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClientExpediteurDTO> getClientExpediteurById(@PathVariable String id) {
        return ResponseEntity.ok(clientExpediteurService.getClientExpediteurById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientExpediteurDTO> updateClientExpediteur(@PathVariable String id, @Valid @RequestBody UpdateClientExpediteurDTO dto) {
        return ResponseEntity.ok(clientExpediteurService.updateClientExpediteur(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientExpediteur(@PathVariable String id) {
        clientExpediteurService.deleteClientExpediteur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ClientExpediteurDTO>> searchClientsExpediteurs(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(clientExpediteurService.searchClientsExpediteurs(keyword, pageable));
    }
}
