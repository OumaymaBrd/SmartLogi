package org.example.smartspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.zone.AddZoneDTO;
import org.example.smartspring.dto.zone.UpdateZoneDTO;
import org.example.smartspring.dto.zone.ZoneDTO;
import org.example.smartspring.service.ZoneService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @PostMapping
    public ResponseEntity<ZoneDTO> createZone(@Valid @RequestBody AddZoneDTO dto) {
        return new ResponseEntity<>(zoneService.createZone(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllZones(
         @RequestParam(defaultValue = "0")   int page,
         @RequestParam(defaultValue = "5")   int size
    ) {
       PageRequest pageable = PageRequest.of(page, size);

        Page<ZoneDTO> pagesZones =zoneService.getAllZones(pageable);
        if (pagesZones.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Votre Page est Vide ! Aucune Resultat pour ca ");
        }
        return ResponseEntity.ok(zoneService.getAllZones(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneDTO> getZoneById(@PathVariable String id) {
        return ResponseEntity.ok(zoneService.getZoneById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZoneDTO> updateZone(@PathVariable String id, @Valid @RequestBody UpdateZoneDTO dto) {
        return ResponseEntity.ok(zoneService.updateZone(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable String id) {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ZoneDTO>> searchZones(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(zoneService.searchZones(keyword, pageable));
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<Map<String, Object>> getZoneStats(@PathVariable String id) {
        return ResponseEntity.ok(zoneService.getZoneStats(id));
    }
}
