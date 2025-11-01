package org.example.smartspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.ClientExpediteur.AddClientExpediteurDTO;
import org.example.smartspring.dto.ClientExpediteur.ClientExpediteurDTO;
import org.example.smartspring.dto.ClientExpediteur.UpdateClientExpediteurDTO;
import org.example.smartspring.service.ClientExpediteurService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients-expediteurs")
@RequiredArgsConstructor
public class ClientExpediteurController {

    private final ClientExpediteurService service;

    @PostMapping
    public ResponseEntity<ClientExpediteurDTO> create(@Valid @RequestBody AddClientExpediteurDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientExpediteurDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ClientExpediteurDTO>> getAll(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ClientExpediteurDTO>> search(
            @RequestParam String keyword,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.search(keyword, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientExpediteurDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateClientExpediteurDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
