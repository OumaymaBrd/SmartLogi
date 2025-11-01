package org.example.smartspring.controller;


import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.LivreurDTO.LivreurDTO;
import org.example.smartspring.repository.LivreurRepository;
import org.example.smartspring.services.LivreurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//Mais je voulais 
import java.util.List;

@RestController
@RequestMapping("/api/livreur")
@RequiredArgsConstructor

public class LivreurController {


    private final LivreurService livreurService;

//    @GetMapping
//    public ResponseEntity<> getAllLivreur(){
//
//        List<LivreurDTO> list= livreurService.getAll()
//        return ResponseEntity.ok(list);
//    }
}
