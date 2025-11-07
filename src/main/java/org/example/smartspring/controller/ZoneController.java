package org.example.smartspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.zone.ZoneDTO;
import org.example.smartspring.service.ZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @PostMapping
    public ResponseEntity<ZoneDTO> creerZone(@RequestBody ZoneDTO dto) {
        ZoneDTO zone = zoneService.creerZone(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(zone);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneDTO> obtenirZone(@PathVariable String id) {
        return zoneService.obtenirZoneParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ZoneDTO>> obtenirToutesLesZones() {
        List<ZoneDTO> zones = zoneService.obtenirToutesLesZones();
        return ResponseEntity.ok(zones);
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<ZoneDTO> obtenirZoneParNom(@PathVariable String nom) {
        return zoneService.obtenirZoneParNom(nom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZoneDTO> modifierZone(
            @PathVariable String id,
            @RequestBody ZoneDTO dto) {
        ZoneDTO zone = zoneService.modifierZone(id, dto);
        return ResponseEntity.ok(zone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerZone(@PathVariable String id) {
        zoneService.supprimerZone(id);
        return ResponseEntity.noContent().build();
    }
}
