package org.example.smartspring.dto.colis.ColisDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.entities.Livreur;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ColisDetailsDTO {
    private String id;
    private String numeroColis;
    private StatutColis statut;
    private PrioriteColis priorite;
    private String ville_destination;
//
    private LivreurCollecteDTO livreurCollecte;
    private LivreurLivreeDTO livreurLivree;

    private String nom_complet;

    private List<ProduitDetailsDTO> produits;

    private DestinataireDetailsDTO destinataire;

    private ZoneDeatailsDTO zone;




}
