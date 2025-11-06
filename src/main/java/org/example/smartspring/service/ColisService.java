package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.colis.ColisDTO;
import org.example.smartspring.entities.*;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;
import org.example.smartspring.exception.ResourceNotFoundException;
import org.example.smartspring.mapper.ColisMapper;
import org.example.smartspring.repository.ClientExpediteurRepository;
import org.example.smartspring.repository.ColisRepository;
import org.example.smartspring.repository.DestinataireRepository;
import org.example.smartspring.repository.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ColisService {

    private final ClientExpediteurRepository clientRepo;
    private final ColisRepository colisRepo;
    private final DestinataireRepository destinataireRepo;
    private final ZoneRepository zoneRepo;
    private final ColisMapper mapper;

    @Transactional
    public Colis creerColisPourNouveauClient(ColisDTO dto) {
        ClientExpediteur client = ClientExpediteur.builder()
                .nom(dto.getExpediteur().getNom())
                .prenom(dto.getExpediteur().getPrenom())
                .email(dto.getExpediteur().getEmail())
                .telephone(dto.getExpediteur().getTelephone())
                .adresse(dto.getExpediteur().getAdresse())
                .ville(dto.getExpediteur().getVille())
                .build();
        client = clientRepo.save(client);

        Destinataire destinataire = Destinataire.builder()
                .nom(dto.getDestinataire().getNom())
                .prenom(dto.getDestinataire().getPrenom())
                .email(dto.getDestinataire().getEmail())
                .telephone(dto.getDestinataire().getTelephone())
                .adresse(dto.getDestinataire().getAdresse())
                .ville(dto.getDestinataire().getVille())
                .build();
        destinataire = destinataireRepo.save(destinataire);

        Zone zone = zoneRepo.findByNom(dto.getZone().getNom())
                .orElseGet(() -> {
                    Zone newZone = Zone.builder()
                            .nom(dto.getZone().getNom())
                            .description(dto.getZone().getDescription())
                            .codePostal(dto.getZone().getCodePostale())
                            .build();
                    return zoneRepo.save(newZone);
                });

        Colis colis = Colis.builder()
                .clientExpediteur(client)
                .destinataire(destinataire)
                .zone(zone)
                .numeroColis(genererNumeroColis())
                .numeroSuivi(genererNumeroSuivi())
                .statut(StatutColis.CREE)
                .priorite(PrioriteColis.NORMALE)
                .dateCreation(LocalDateTime.now())
                .dateLivraisonReelle(null)
                .livreur(null)
                .villeDestination(dto.getDestinataire().getVille())
                .produits(new ArrayList<>())
                .build();

        if (dto.getProduits() != null && !dto.getProduits().isEmpty()) {
            List<Produit> produits = dto.getProduits().stream()
                    .map(produitDTO -> Produit.builder()
                            .nom(produitDTO.getNom())
                            .description(produitDTO.getDescription())
                            .quantite(produitDTO.getQuantite())
                            .colis(colis)
                            .build())
                    .toList();
            colis.setProduits(produits);
        }

        return colisRepo.save(colis);
    }

    @Transactional
    public Colis creerColisPourClientExistant(String clientId, ColisDTO dto) {
        ClientExpediteur client = clientRepo.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé"));

        Destinataire destinataire = Destinataire.builder()
                .nom(dto.getDestinataire().getNom())
                .prenom(dto.getDestinataire().getPrenom())
                .email(dto.getDestinataire().getEmail())
                .telephone(dto.getDestinataire().getTelephone())
                .adresse(dto.getDestinataire().getAdresse())
                .ville(dto.getDestinataire().getVille())
                .build();
        destinataire = destinataireRepo.save(destinataire);

        Zone zone = zoneRepo.findByNom(dto.getZone().getNom())
                .orElseGet(() -> {
                    Zone newZone = Zone.builder()
                            .nom(dto.getZone().getNom())
                            .description(dto.getZone().getDescription())
                            .codePostal(dto.getZone().getCodePostale())
                            .build();
                    return zoneRepo.save(newZone);
                });

        Colis colis = Colis.builder()
                .clientExpediteur(client)
                .destinataire(destinataire)
                .zone(zone)
                .numeroColis(genererNumeroColis())
                .numeroSuivi(genererNumeroSuivi())
                .statut(StatutColis.CREE)
                .priorite(PrioriteColis.NORMALE)
                .dateCreation(LocalDateTime.now())
                .dateLivraisonReelle(null)
                .livreur(null)
                .villeDestination(dto.getDestinataire().getVille())
                .produits(new ArrayList<>())
                .build();

        if (dto.getProduits() != null && !dto.getProduits().isEmpty()) {
            List<Produit> produits = dto.getProduits().stream()
                    .map(produitDTO -> Produit.builder()
                            .nom(produitDTO.getNom())
                            .description(produitDTO.getDescription())
                            .quantite(produitDTO.getQuantite())
                            .colis(colis)
                            .build())
                    .toList();
            colis.setProduits(produits);
        }

        return colisRepo.save(colis);
    }

    @Transactional(readOnly = true)
    public Optional<Colis> getColisById(String id) {
        return colisRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Colis> getColisByClientExpediteur(String clientId) {
        return colisRepo.findByClientExpediteur_Id(clientId);
    }

    @Transactional(readOnly = true)
    public List<Colis> getAllColis() {
        return colisRepo.findAll();
    }

    @Transactional
    public Colis updateColis(String colisId, ColisDTO dto) {
        Colis colis = colisRepo.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        if (dto.getStatut() != null) {
            colis.setStatut(StatutColis.valueOf(dto.getStatut()));
        }

        return colisRepo.save(colis);
    }

    @Transactional
    public void deleteColis(String colisId) {
        Colis colis = colisRepo.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));
        colisRepo.delete(colis);
    }

    private String genererNumeroColis() {
        return "COLIS_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String genererNumeroSuivi() {
        return "SUIVI_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
