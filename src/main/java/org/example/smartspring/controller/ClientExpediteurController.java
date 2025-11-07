package org.example.smartspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.clientexpediteur.ClientExpediteurDTO;
import org.example.smartspring.service.ClientExpediteurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientExpediteurController {

    private final ClientExpediteurService clientService;

    @PostMapping
    public ResponseEntity<ClientExpediteurDTO> creerClient(@RequestBody ClientExpediteurDTO dto) {
        ClientExpediteurDTO client = clientService.creerClient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientExpediteurDTO> obtenirClient(@PathVariable String id) {
        return clientService.obtenirClientParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ClientExpediteurDTO>> obtenirTousLesClients() {
        List<ClientExpediteurDTO> clients = clientService.obtenirTousLesClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClientExpediteurDTO> obtenirClientParEmail(@PathVariable String email) {
        return clientService.obtenirClientParEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/telephone/{telephone}")
    public ResponseEntity<ClientExpediteurDTO> obtenirClientParTelephone(@PathVariable String telephone) {
        return clientService.obtenirClientParTelephone(telephone)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientExpediteurDTO> modifierClient(
            @PathVariable String id,
            @RequestBody ClientExpediteurDTO dto) {
        ClientExpediteurDTO client = clientService.modifierClient(id, dto);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerClient(@PathVariable String id) {
        clientService.supprimerClient(id);
        return ResponseEntity.noContent().build();
    }
}
