package org.example.smartspring.services;

import org.example.smartspring.dto.LivreurDTO.LivreurDTO;
import org.example.smartspring.entities.Livreur;
import org.example.smartspring.mapper.LivreurMapper;
import org.example.smartspring.repository.LivreurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LivreurService {
    @Autowired
    private LivreurRepository livreurRepository;

    private LivreurMapper livreurMapper;

    public LivreurDTO getAll(String id){
        Optional<Livreur> livreur = this.livreurRepository.findById(id);
        return this.livreurMapper.toDto(livreur.get());
    }
}
