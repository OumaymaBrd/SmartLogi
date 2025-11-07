package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.clientexpediteur.ClientExpediteurDTO;
import org.example.smartspring.entities.ClientExpediteur;
import org.example.smartspring.mapper.ClientExpediteurMapper;
import org.example.smartspring.repository.ClientExpediteurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientExpediteurService {

    private final ClientExpediteurRepository clientRepository;
    private final ClientExpediteurMapper clientMapper;

    @Transactional
    public ClientExpediteurDTO creerClient(ClientExpediteurDTO dto) {
        ClientExpediteur client = clientMapper.toEntity(dto);
        ClientExpediteur saved = clientRepository.save(client);
        return clientMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public ClientExpediteurDTO obtenirClient(String id) {
        ClientExpediteur client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID: " + id));
        return clientMapper.toDTO(client);
    }

    @Transactional(readOnly = true)
    public Optional<ClientExpediteurDTO> obtenirClientParId(String id) {
        return clientRepository.findById(id)
                .map(clientMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<ClientExpediteurDTO> obtenirTousLesClients() {
        return clientMapper.toDTOList(clientRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<ClientExpediteurDTO> rechercherClients(String keyword) {
        return clientMapper.toDTOList(clientRepository.rechercherClients(keyword));
    }

    @Transactional(readOnly = true)
    public List<ClientExpediteurDTO> obtenirClientsParVille(String ville) {
        return clientMapper.toDTOList(clientRepository.findByVille(ville));
    }

    @Transactional(readOnly = true)
    public Optional<ClientExpediteurDTO> obtenirClientParEmail(String email) {
        return clientRepository.findByEmail(email)
                .map(clientMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<ClientExpediteurDTO> obtenirClientParTelephone(String telephone) {
        return clientRepository.findByTelephone(telephone)
                .map(clientMapper::toDTO);
    }

    @Transactional
    public ClientExpediteurDTO modifierClient(String id, ClientExpediteurDTO dto) {
        ClientExpediteur client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID: " + id));

        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        client.setEmail(dto.getEmail());
        client.setTelephone(dto.getTelephone());
        client.setAdresse(dto.getAdresse());
        client.setVille(dto.getVille());

        ClientExpediteur updated = clientRepository.save(client);
        return clientMapper.toDTO(updated);
    }

    @Transactional
    public void supprimerClient(String id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client non trouvé avec l'ID: " + id);
        }
        clientRepository.deleteById(id);
    }
}
