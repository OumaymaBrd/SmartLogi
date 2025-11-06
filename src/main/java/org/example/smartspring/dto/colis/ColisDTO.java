package org.example.smartspring.dto.colis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.dto.clientexpediteur.ClientExpediteurDTO;
import org.example.smartspring.dto.destinataire.DestinataireDTO;
import org.example.smartspring.dto.livreur.LivreurDTO;
import org.example.smartspring.dto.produit.ProduitDTO;
import org.example.smartspring.dto.zone.ZoneDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColisDTO {
    private String statut;
    private ClientExpediteurDTO expediteur;
    private DestinataireDTO destinataire;
    private List<ProduitDTO> produits;
    private ZoneDTO zone;
    private String description;
    private Double poids;
}
