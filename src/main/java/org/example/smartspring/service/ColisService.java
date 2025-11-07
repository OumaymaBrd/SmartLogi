package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.colis.*;
import org.example.smartspring.entities.*;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;
import org.example.smartspring.exception.ResourceNotFoundException;
import org.example.smartspring.mapper.ColisMapper;
import org.example.smartspring.mapper.HistoriqueLivraisonMapper;
import org.example.smartspring.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColisService {

    private final ClientExpediteurRepository clientRepo;
    private final ColisRepository colisRepo;
    private final DestinataireRepository destinataireRepo;
    private final ZoneRepository zoneRepo;
    private final ProduitRepository produitRepo;
    private final ColisProduitRepository colisProduitRepo;
    private final ColisMapper mapper;
    private final HistoriqueLivraisonMapper historiqueMapper;
    private final HistoriqueLivraisonRepository historiqueRepo;
    private final LivreurRepository livreurRepo;
    private final GestionnaireLogistiqueRepository gestionnaireRepo;

    @Transactional
    public ColisDTO creerColisPourNouveauClient(ColisDTO dto) {
        ClientExpediteur client = mapper.toClientExpediteur(dto.getExpediteur());
        client = clientRepo.save(client);

        Destinataire destinataire = mapper.toDestinataire(dto.getDestinataire());
        destinataire = destinataireRepo.save(destinataire);

        Zone zone = zoneRepo.findByNom(dto.getZone().getNom())
                .orElseGet(() -> {
                    Zone newZone = mapper.toZone(dto.getZone());
                    return zoneRepo.save(newZone);
                });

        PrioriteColis priorite = (dto.getPriorite() != null && !dto.getPriorite().isEmpty())
                ? PrioriteColis.valueOf(dto.getPriorite().toUpperCase())
                : PrioriteColis.NORMALE;

        Colis colis = Colis.builder()
                .clientExpediteur(client)
                .destinataire(destinataire)
                .zone(zone)
                .numeroColis(genererNumeroColis())
                .numeroSuivi(genererNumeroSuivi())
                .statut(StatutColis.CREE)
                .priorite(priorite)
                .dateCreation(LocalDateTime.now())
                .dateLivraisonReelle(null)
                .livreur(null)
                .villeDestination(dto.getDestinataire().getVille())
                .colisProduits(new ArrayList<>())
                .build();

        colis = colisRepo.save(colis);

        if (dto.getProduits() != null && !dto.getProduits().isEmpty()) {
            for (var produitDTO : dto.getProduits()) {
                Produit produit = produitRepo.findByNom(produitDTO.getNom())
                        .orElseGet(() -> {
                            Produit newProduit = mapper.toProduit(produitDTO);
                            newProduit.setPrixUnitaire(BigDecimal.valueOf(30.00));
                            return produitRepo.save(newProduit);
                        });

                BigDecimal prixTotal = produit.getPrixUnitaire()
                        .multiply(BigDecimal.valueOf(produitDTO.getQuantite()));

                ColisProduit colisProduit = ColisProduit.builder()
                        .colis(colis)
                        .produit(produit)
                        .quantite(produitDTO.getQuantite())
                        .prix(prixTotal)
                        .dateAjout(LocalDateTime.now())
                        .build();

                colis.getColisProduits().add(colisProduit);
            }
        }

        Colis savedColis = colisRepo.save(colis);
        return mapper.toDTO(savedColis);
    }

    @Transactional
    public ColisDTO creerColisPourClientExistant(String clientId, ColisDTO dto) {
        ClientExpediteur client = clientRepo.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé"));

        Destinataire destinataire = mapper.toDestinataire(dto.getDestinataire());
        destinataire = destinataireRepo.save(destinataire);

        Zone zone = zoneRepo.findByNom(dto.getZone().getNom())
                .orElseGet(() -> {
                    Zone newZone = mapper.toZone(dto.getZone());
                    return zoneRepo.save(newZone);
                });

        PrioriteColis priorite = (dto.getPriorite() != null && !dto.getPriorite().isEmpty())
                ? PrioriteColis.valueOf(dto.getPriorite().toUpperCase())
                : PrioriteColis.NORMALE;

        Colis colis = Colis.builder()
                .clientExpediteur(client)
                .destinataire(destinataire)
                .zone(zone)
                .numeroColis(genererNumeroColis())
                .numeroSuivi(genererNumeroSuivi())
                .statut(StatutColis.CREE)
                .priorite(priorite)
                .dateCreation(LocalDateTime.now())
                .dateLivraisonReelle(null)
                .livreur(null)
                .villeDestination(dto.getDestinataire().getVille())
                .colisProduits(new ArrayList<>())
                .build();

        colis = colisRepo.save(colis);

        if (dto.getProduits() != null && !dto.getProduits().isEmpty()) {
            for (var produitDTO : dto.getProduits()) {
                Produit produit = produitRepo.findByNom(produitDTO.getNom())
                        .orElseGet(() -> {
                            Produit newProduit = mapper.toProduit(produitDTO);
                            newProduit.setPrixUnitaire(BigDecimal.valueOf(30.00));
                            return produitRepo.save(newProduit);
                        });

                BigDecimal prixTotal = produit.getPrixUnitaire()
                        .multiply(BigDecimal.valueOf(produitDTO.getQuantite()));

                ColisProduit colisProduit = ColisProduit.builder()
                        .colis(colis)
                        .produit(produit)
                        .quantite(produitDTO.getQuantite())
                        .prix(prixTotal)
                        .dateAjout(LocalDateTime.now())
                        .build();

                colis.getColisProduits().add(colisProduit);
            }
        }

        Colis savedColis = colisRepo.save(colis);
        return mapper.toDTO(savedColis);
    }

    @Transactional(readOnly = true)
    public Optional<Colis> getColisById(String id) {
        return colisRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<ColisDTO> getColisDTOById(String id) {
        return colisRepo.findById(id).map(mapper::toDTO);
    }

    @Transactional
    public Colis assignerColisALivreur(String gestionnaireId, String colisId, AssignerLivreurDTO dto) {
        Colis colis = colisRepo.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        Livreur livreur = livreurRepo.findById(dto.getLivreurId())
                .orElseThrow(() -> new ResourceNotFoundException("Livreur non trouvé"));

        GestionnaireLogistique gestionnaire = gestionnaireRepo.findById(gestionnaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Gestionnaire non trouvé"));

        String ancienLivreur = colis.getLivreur() != null ? colis.getLivreur().getNom() : "Aucun";
        colis.setLivreur(livreur);
        colis.setStatut(StatutColis.ASSIGNE);

        HistoriqueLivraison historique = HistoriqueLivraison.builder()
                .colis(colis)
                .gestionnaire(gestionnaire)
                .action("ASSIGNE")
                .statut(StatutColis.ASSIGNE)
                .commentaire(dto.getCommentaire() != null ? dto.getCommentaire() : "Colis assigné au livreur " + livreur.getNom())
                .dateChangement(LocalDateTime.now())
                .build();

        historiqueRepo.save(historique);

        return colisRepo.save(colis);
    }

    @Transactional
    public ColisDTO assignerColisAuLivreurDTO(String colisId, String livreurId, String gestionnaireId) {
        Colis colis = colisRepo.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        Livreur livreur = livreurRepo.findById(livreurId)
                .orElseThrow(() -> new ResourceNotFoundException("Livreur non trouvé"));

        GestionnaireLogistique gestionnaire = gestionnaireRepo.findById(gestionnaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Gestionnaire non trouvé"));

        colis.setLivreur(livreur);
        colis.setStatut(StatutColis.COLLECTE);

        HistoriqueLivraison historique = HistoriqueLivraison.builder()
                .colis(colis)
                .gestionnaire(gestionnaire)
                .action("ASSIGNE")
                .statut(StatutColis.COLLECTE)
                .commentaire("Colis assigné au livreur " + livreur.getNom() + " pour collecte")
                .dateChangement(LocalDateTime.now())
                .build();

        historiqueRepo.save(historique);

        Colis savedColis = colisRepo.save(colis);
        return mapper.toDTO(savedColis);
    }

    @Transactional(readOnly = true)
    public List<Colis> getColisByClientExpediteur(String clientId) {
        return colisRepo.findByClientExpediteur_Id(clientId);
    }

    @Transactional(readOnly = true)
    public List<ColisDTO> getAllColisDTO() {
        return colisRepo.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Colis> getAllColis() {
        return colisRepo.findAll();
    }

    @Transactional
    public ColisDTO updateColis(String colisId, ColisDTO dto) {
        Colis colis = colisRepo.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        if (dto.getStatut() != null) {
            colis.setStatut(StatutColis.valueOf(dto.getStatut()));
        }

        Colis savedColis = colisRepo.save(colis);
        return mapper.toDTO(savedColis);
    }

    @Transactional
    public Colis modifierColis(String colisId, ColisDTO dto) {
        Colis colis = colisRepo.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        StringBuilder modifications = new StringBuilder();

        if (dto.getPriorite() != null && !dto.getPriorite().isEmpty()) {
            PrioriteColis nouvellePriorite = PrioriteColis.valueOf(dto.getPriorite().toUpperCase());
            if (colis.getPriorite() != nouvellePriorite) {
                modifications.append("Priorité: ").append(colis.getPriorite())
                        .append(" → ").append(nouvellePriorite).append("; ");
                colis.setPriorite(nouvellePriorite);
            }
        }

        if (dto.getDestinataire() != null) {
            if (dto.getDestinataire().getNom() != null) {
                modifications.append("Destinataire modifié; ");
                colis.getDestinataire().setNom(dto.getDestinataire().getNom());
                colis.getDestinataire().setPrenom(dto.getDestinataire().getPrenom());
                colis.getDestinataire().setTelephone(dto.getDestinataire().getTelephone());
                colis.getDestinataire().setAdresse(dto.getDestinataire().getAdresse());
                colis.getDestinataire().setVille(dto.getDestinataire().getVille());
            }
        }

        if (modifications.length() > 0) {
            HistoriqueLivraison historique = HistoriqueLivraison.builder()
                    .colis(colis)
                    .action("MODIFIE")
                    .statut(colis.getStatut())
                    .commentaire(modifications.toString())
                    .dateChangement(LocalDateTime.now())
                    .build();

            historiqueRepo.save(historique);
        }

        return colisRepo.save(colis);
    }

    @Transactional
    public ColisDTO modifierColisParGestionnaireDTO(String colisId, ColisDTO dto, String gestionnaireId) {
        Colis colis = colisRepo.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        GestionnaireLogistique gestionnaire = gestionnaireRepo.findById(gestionnaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Gestionnaire non trouvé"));

        StringBuilder modifications = new StringBuilder();

        if (dto.getPriorite() != null && !dto.getPriorite().isEmpty()) {
            PrioriteColis nouvellePriorite = PrioriteColis.valueOf(dto.getPriorite().toUpperCase());
            if (colis.getPriorite() != nouvellePriorite) {
                modifications.append("Priorité: ").append(colis.getPriorite())
                        .append(" → ").append(nouvellePriorite).append("; ");
                colis.setPriorite(nouvellePriorite);
            }
        }

        if (dto.getDestinataire() != null) {
            if (dto.getDestinataire().getNom() != null) {
                modifications.append("Destinataire modifié; ");
                colis.getDestinataire().setNom(dto.getDestinataire().getNom());
                colis.getDestinataire().setPrenom(dto.getDestinataire().getPrenom());
                colis.getDestinataire().setTelephone(dto.getDestinataire().getTelephone());
                colis.getDestinataire().setAdresse(dto.getDestinataire().getAdresse());
                colis.getDestinataire().setVille(dto.getDestinataire().getVille());
            }
        }

        if (modifications.length() > 0) {
            HistoriqueLivraison historique = HistoriqueLivraison.builder()
                    .colis(colis)
                    .gestionnaire(gestionnaire)
                    .action("MODIFIE")
                    .statut(colis.getStatut())
                    .commentaire(modifications.toString())
                    .dateChangement(LocalDateTime.now())
                    .build();

            historiqueRepo.save(historique);
        }

        Colis savedColis = colisRepo.save(colis);
        return mapper.toDTO(savedColis);
    }

    @Transactional
    public Colis modifierColisParGestionnaire(String colisId, ColisDTO dto, String gestionnaireId) {
        Colis colis = colisRepo.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        GestionnaireLogistique gestionnaire = gestionnaireRepo.findById(gestionnaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Gestionnaire non trouvé"));

        StringBuilder modifications = new StringBuilder();

        if (dto.getPriorite() != null && !dto.getPriorite().isEmpty()) {
            PrioriteColis nouvellePriorite = PrioriteColis.valueOf(dto.getPriorite().toUpperCase());
            if (colis.getPriorite() != nouvellePriorite) {
                modifications.append("Priorité: ").append(colis.getPriorite())
                        .append(" → ").append(nouvellePriorite).append("; ");
                colis.setPriorite(nouvellePriorite);
            }
        }

        if (dto.getDestinataire() != null) {
            if (dto.getDestinataire().getNom() != null) {
                modifications.append("Destinataire modifié; ");
                colis.getDestinataire().setNom(dto.getDestinataire().getNom());
                colis.getDestinataire().setPrenom(dto.getDestinataire().getPrenom());
                colis.getDestinataire().setTelephone(dto.getDestinataire().getTelephone());
                colis.getDestinataire().setAdresse(dto.getDestinataire().getAdresse());
                colis.getDestinataire().setVille(dto.getDestinataire().getVille());
            }
        }

        if (modifications.length() > 0) {
            HistoriqueLivraison historique = HistoriqueLivraison.builder()
                    .colis(colis)
                    .gestionnaire(gestionnaire)
                    .action("MODIFIE")
                    .statut(colis.getStatut())
                    .commentaire(modifications.toString())
                    .dateChangement(LocalDateTime.now())
                    .build();

            historiqueRepo.save(historique);
        }

        return colisRepo.save(colis);
    }

    @Transactional
    public void deleteColis(String colisId) {
        Colis colis = colisRepo.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));
        colisRepo.delete(colis);
    }

    @Transactional
    public void supprimerColisParGestionnaire(String colisId, String gestionnaireId) {
        Colis colis = colisRepo.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        GestionnaireLogistique gestionnaire = gestionnaireRepo.findById(gestionnaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Gestionnaire non trouvé"));

        HistoriqueLivraison historique = HistoriqueLivraison.builder()
                .colis(colis)
                .gestionnaire(gestionnaire)
                .action("SUPPRIME")
                .statut(colis.getStatut())
                .commentaire("Colis supprimé par le gestionnaire")
                .dateChangement(LocalDateTime.now())
                .build();

        historiqueRepo.save(historique);
        colisRepo.delete(colis);
    }

    @Transactional(readOnly = true)
    public Page<ColisDTO> getTousLesColisDTO(Pageable pageable) {
        return colisRepo.findAll(pageable).map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<ColisDTO> filtrerColisDTO(ColisFilterDTO filter, Pageable pageable) {
        Sort sort = filter.getSortDirection().equalsIgnoreCase("ASC")
                ? Sort.by(filter.getSortBy()).ascending()
                : Sort.by(filter.getSortBy()).descending();

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<Colis> colisPage = colisRepo.filtrerColis(
                filter.getStatut(),
                filter.getPriorite(),
                filter.getZoneNom(),
                filter.getVille(),
                filter.getDateCreationDebut(),
                filter.getDateCreationFin(),
                filter.getLivreurId(),
                filter.getMotCle(),
                sortedPageable
        );

        return colisPage.map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<Colis> filtrerColis(ColisFilterDTO filter) {
        Sort sort = filter.getSortDirection().equalsIgnoreCase("ASC")
                ? Sort.by(filter.getSortBy()).ascending()
                : Sort.by(filter.getSortBy()).descending();

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);

        return colisRepo.filtrerColis(
                filter.getStatut(),
                filter.getPriorite(),
                filter.getZoneNom(),
                filter.getVille(),
                filter.getDateCreationDebut(),
                filter.getDateCreationFin(),
                filter.getLivreurId(),
                filter.getMotCle(),
                pageable
        );
    }

    @Transactional(readOnly = true)
    public StatistiquesDTO obtenirStatistiques() {
        Long totalColis = colisRepo.count();

        Map<String, Long> colisParStatut = colisRepo.countColisParStatut().stream()
                .collect(Collectors.toMap(
                        arr -> arr[0].toString(),
                        arr -> (Long) arr[1]
                ));

        Map<String, Long> colisParPriorite = colisRepo.countColisParPriorite().stream()
                .collect(Collectors.toMap(
                        arr -> arr[0].toString(),
                        arr -> (Long) arr[1]
                ));

        Map<String, Long> colisParZone = colisRepo.countColisParZone().stream()
                .collect(Collectors.toMap(
                        arr -> arr[0].toString(),
                        arr -> (Long) arr[1]
                ));

        Map<String, StatistiquesLivreurDTO> statsLivreur = new HashMap<>();
        colisRepo.statistiquesParLivreur().forEach(arr -> {
            String livreurId = (String) arr[0];
            String livreurNom = (String) arr[1];
            Long nombreColis = (Long) arr[2];
            BigDecimal poidsTotal = (BigDecimal) arr[3];

            statsLivreur.put(livreurId, StatistiquesLivreurDTO.builder()
                    .livreurId(livreurId)
                    .livreurNom(livreurNom)
                    .nombreColis(nombreColis)
                    .poidsTotal(poidsTotal)
                    .build());
        });

        Long colisPrioritaires = colisRepo.countColisPrioritaires();
        Long colisEnRetard = colisRepo.countColisEnRetard(LocalDateTime.now().minusDays(3));

        return StatistiquesDTO.builder()
                .totalColis(totalColis)
                .colisParStatut(colisParStatut)
                .colisParPriorite(colisParPriorite)
                .colisParZone(colisParZone)
                .statistiquesParLivreur(statsLivreur)
                .colisPrioritaires(colisPrioritaires)
                .colisEnRetard(colisEnRetard)
                .build();
    }

    @Transactional(readOnly = true)
    public List<HistoriqueColisDTO> obtenirHistoriqueColis(String colisId) {
        List<HistoriqueLivraison> historiques = historiqueRepo.findByColisIdOrderByDateChangementDesc(colisId);

        return historiqueMapper.toDTOList(historiques);
    }

    @Transactional(readOnly = true)
    public List<ColisDTO> getColisParLivreur(String livreurId) {
        Livreur livreur = livreurRepo.findById(livreurId)
                .orElseThrow(() -> new ResourceNotFoundException("Livreur non trouvé"));

        List<Colis> colisList = colisRepo.findByLivreur_Id(livreurId);
        return colisList.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ColisDTO changerStatutParLivreur(String livreurId, String colisId, ChangerStatutDTO dto) {
        Colis colis = colisRepo.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        Livreur livreur = livreurRepo.findById(livreurId)
                .orElseThrow(() -> new ResourceNotFoundException("Livreur non trouvé"));

        // Verify that the colis is assigned to this livreur
        if (colis.getLivreur() == null || !colis.getLivreur().getId().equals(livreurId)) {
            throw new IllegalStateException("Ce colis n'est pas assigné à ce livreur");
        }

        StatutColis ancienStatut = colis.getStatut();
        StatutColis nouveauStatut = StatutColis.valueOf(dto.getNouveauStatut().toUpperCase());

        // Validate status transitions
        validateStatusTransition(ancienStatut, nouveauStatut);

        colis.setStatut(nouveauStatut);

        // If status is LIVRE, set delivery date
        if (nouveauStatut == StatutColis.LIVRE) {
            colis.setDateLivraisonReelle(LocalDateTime.now());
        }

        HistoriqueLivraison historique = HistoriqueLivraison.builder()
                .colis(colis)
                .livreur(livreur)
                .action("CHANGEMENT_STATUT")
                .statut(nouveauStatut)
                .commentaire(dto.getCommentaire() != null ? dto.getCommentaire() :
                        "Statut changé de " + ancienStatut + " à " + nouveauStatut)
                .dateChangement(LocalDateTime.now())
                .build();

        historiqueRepo.save(historique);

        Colis savedColis = colisRepo.save(colis);
        return mapper.toDTO(savedColis);
    }

    private void validateStatusTransition(StatutColis ancienStatut, StatutColis nouveauStatut) {
        // COLLECTE → EN_STOCK (livreur collecte et met en stock)
        if (ancienStatut == StatutColis.COLLECTE && nouveauStatut == StatutColis.EN_STOCK) {
            return;
        }

        // EN_STOCK → EN_TRANSIT (livreur prend le colis pour livraison)
        if (ancienStatut == StatutColis.EN_STOCK && nouveauStatut == StatutColis.EN_TRANSIT) {
            return;
        }

        // EN_TRANSIT → LIVRE (livreur livre le colis)
        if (ancienStatut == StatutColis.EN_TRANSIT && nouveauStatut == StatutColis.LIVRE) {
            return;
        }

        // Any status → ANNULE (can be cancelled at any time)
        if (nouveauStatut == StatutColis.ANNULE) {
            return;
        }

        throw new IllegalStateException(
                "Transition de statut invalide: " + ancienStatut + " → " + nouveauStatut
        );
    }

    @Transactional
    public ColisDTO reassignerLivreur(String gestionnaireId, String colisId, String nouveauLivreurId, String commentaire) {
        Colis colis = colisRepo.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        Livreur nouveauLivreur = livreurRepo.findById(nouveauLivreurId)
                .orElseThrow(() -> new ResourceNotFoundException("Livreur non trouvé"));

        GestionnaireLogistique gestionnaire = gestionnaireRepo.findById(gestionnaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Gestionnaire non trouvé"));

        // Verify that colis is EN_STOCK before reassigning
        if (colis.getStatut() != StatutColis.EN_STOCK) {
            throw new IllegalStateException("Le colis doit être EN_STOCK pour être réassigné");
        }

        String ancienLivreur = colis.getLivreur() != null ? colis.getLivreur().getNom() : "Aucun";
        colis.setLivreur(nouveauLivreur);

        HistoriqueLivraison historique = HistoriqueLivraison.builder()
                .colis(colis)
                .gestionnaire(gestionnaire)
                .action("REASSIGNE")
                .statut(StatutColis.EN_STOCK)
                .commentaire(commentaire != null ? commentaire :
                        "Colis réassigné de " + ancienLivreur + " à " + nouveauLivreur.getNom())
                .dateChangement(LocalDateTime.now())
                .build();

        historiqueRepo.save(historique);

        Colis savedColis = colisRepo.save(colis);
        return mapper.toDTO(savedColis);
    }

    private String genererNumeroColis() {
        return "COLIS_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String genererNumeroSuivi() {
        return "SUIVI_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
