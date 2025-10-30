package org.example.smartspring.services;

import org.example.smartspring.dto.ClientExpediteur.ClientExpediteurDTO;
import org.example.smartspring.dto.ClientExpediteur.UpdateClientExpediteurDTO;
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
        ClientExpediteur savedEntity = clientExpediteurRepository.save(entity);
        return clientExpediteurMapper.toDto(savedEntity);
    }

    public ClientExpediteurDTO update(UpdateClientExpediteurDTO dto) {

        //  Récupérer l'entité existante
        ClientExpediteur existing = clientExpediteurRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        //  Mettre à jour les champs de l'entité existante
        clientExpediteurMapper.updateEntityFromDto(dto, existing);

        //  Sauvegarder
        ClientExpediteur saved = clientExpediteurRepository.save(existing);

        // Convertir en DTO
        return clientExpediteurMapper.toDto(saved);
    }

    public void delete(String id) {
        ClientExpediteur existing = clientExpediteurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        clientExpediteurRepository.delete(existing);
    }
}
