package org.example.smartspring.services;

import org.example.smartspring.dto.CllientExpediteur.ClientExpediteurDTO;
import org.example.smartspring.entities.ClientExpediteur;
import org.example.smartspring.mapper.ClientExpediteurMapper;
import org.example.smartspring.repository.ClientExpediteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientExpediteurService {
    @Autowired
    private  ClientExpediteurMapper clientExpediteurMapper;

    @Autowired
    private ClientExpediteurRepository  clientExpediteurRepository;


    public ClientExpediteurDTO get(String id){
        Optional<ClientExpediteur> clientExpediteur=this.clientExpediteurRepository.findById(id);
        return this.clientExpediteurMapper.toDto(clientExpediteur.get());
    }


    public ClientExpediteurDTO save(ClientExpediteurDTO dto) {
        // Convertir le DTO en entité
        ClientExpediteur entity = clientExpediteurMapper.toEntity(dto);

        // Sauvegarder dans la base
        ClientExpediteur savedEntity = clientExpediteurRepository.save(entity);

        // Retourner le DTO sauvegardé
        return clientExpediteurMapper.toDto(savedEntity);
    }
}
