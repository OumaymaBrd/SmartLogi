package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.livreur.LivreurDTO;
import org.example.smartspring.entities.Livreur;
import org.example.smartspring.mapper.LivreurMapper;
import org.example.smartspring.repository.LivreurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LivreurService {

    private final LivreurRepository livreurRepository;
    private final LivreurMapper livreurMapper;

    @Transactional
    public LivreurDTO creerLivreur(LivreurDTO dto) {
        Livreur livreur = livreurMapper.toEntity(dto);
        Livreur saved = livreurRepository.save(livreur);
        return livreurMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public LivreurDTO obtenirLivreur(String id) {
        Livreur livreur = livreurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livreur non trouvé avec l'ID: " + id));
        return livreurMapper.toDTO(livreur);
    }

    @Transactional(readOnly = true)
    public Optional<LivreurDTO> obtenirLivreurParId(String id) {
        return livreurRepository.findById(id)
                .map(livreurMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<LivreurDTO> obtenirTousLesLivreurs() {
        return livreurMapper.toDTOList(livreurRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<LivreurDTO> obtenirLivreursActifs() {
        return livreurMapper.toDTOList(livreurRepository.findAllActifs());
    }

    @Transactional(readOnly = true)
    public List<LivreurDTO> rechercherLivreurs(String keyword) {
        return livreurMapper.toDTOList(livreurRepository.rechercherLivreurs(keyword));
    }

    @Transactional(readOnly = true)
    public List<LivreurDTO> obtenirLivreursParZone(String zoneNom) {
        return livreurMapper.toDTOList(livreurRepository.findByZoneNom(zoneNom));
    }

    @Transactional(readOnly = true)
    public List<LivreurDTO> obtenirLivreursDisponibles() {
        return livreurMapper.toDTOList(livreurRepository.findByActif(true));
    }

    @Transactional
    public LivreurDTO modifierLivreur(String id, LivreurDTO dto) {
        Livreur livreur = livreurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livreur non trouvé avec l'ID: " + id));

        livreur.setNom(dto.getNom());
        livreur.setPrenom(dto.getPrenom());
        livreur.setTelephone(dto.getTelephone());
        livreur.setEmail(dto.getEmail());
        livreur.setNumeroPermis(dto.getNumeroPermis());
        livreur.setActif(dto.getActif());

        Livreur updated = livreurRepository.save(livreur);
        return livreurMapper.toDTO(updated);
    }

    @Transactional
    public void supprimerLivreur(String id) {
        if (!livreurRepository.existsById(id)) {
            throw new RuntimeException("Livreur non trouvé avec l'ID: " + id);
        }
        livreurRepository.deleteById(id);
    }

    @Transactional
    public LivreurDTO desactiverLivreur(String id) {
        Livreur livreur = livreurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livreur non trouvé avec l'ID: " + id));
        livreur.setActif(false);
        Livreur updated = livreurRepository.save(livreur);
        return livreurMapper.toDTO(updated);
    }
}
