package org.example.smartspring.dto.colis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.dto.clientexpediteur.ClientExpediteurDTO;
import org.example.smartspring.dto.destinataire.DestinataireDTO;
import org.example.smartspring.dto.livreur.LivreurDTO;
import org.example.smartspring.dto.zone.ZoneDTO;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColisDTO {

    private String id;
    private String numeroSuivi;
    private BigDecimal poids;
    private BigDecimal valeurDeclaree;
    private String description;
    private StatutColis statut;
    private PrioriteColis priorite;
    private LocalDateTime dateCreation;
    private LocalDateTime dateLivraison;
    private ClientExpediteurDTO clientExpediteur;
    private DestinataireDTO destinataire;
    private LivreurDTO livreur;
    private ZoneDTO zone;
}
