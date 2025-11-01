package org.example.smartspring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.smartspring.dto.response.ColisResponseDTO;
import org.example.smartspring.dto.request.ColisRequestDTO;
import org.example.smartspring.services.ColisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/colis")
@RequiredArgsConstructor
public class ColisController {

    private final ColisService colisService;

    @PostMapping
    public ResponseEntity<ColisResponseDTO> createColis(@RequestBody ColisRequestDTO dto) {
        return new ResponseEntity<>(colisService.createColis(dto), HttpStatus.CREATED);
    }



    @GetMapping
    public ResponseEntity<?> getAllColis(
            @RequestParam(required = false) String description) {

        List<ColisResponseDTO> list = colisService.getAllColis(description);

        if (list == null || list.isEmpty()) {
            Map<String, String> message = new HashMap<>();
            message.put("message", "Aucun colis trouvé dans votre stock.");
            log.info("Aucun colis trouv<UNK> dans votre stock.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(list);
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getColisById(@PathVariable String id) {
        ColisResponseDTO dto = colisService.getColisById(id);
        if (dto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Aucun colis trouvé avec l'ID : " + id);
        }
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColis(@PathVariable String id) {
        colisService.deleteColisById(id);
        return ResponseEntity.noContent().build();
    }
}
