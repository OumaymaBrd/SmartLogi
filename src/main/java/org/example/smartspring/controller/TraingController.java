package org.example.smartspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.training.TrainingDTO;
import org.example.smartspring.service.training.TraineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trainees")
@RequiredArgsConstructor
public class TraingController {

    private final TraineService traineService;

    @GetMapping("/all")
    public ResponseEntity<List<TrainingDTO>> getAllTraining() {
        List<TrainingDTO> dtos = traineService.getAll();
        return ResponseEntity.ok(dtos);
    }
}
