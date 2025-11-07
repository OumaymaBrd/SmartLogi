package org.example.smartspring.dto.colis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.dto.destinataire.DestinataireDTO;
import org.example.smartspring.dto.produit.ProduitDTO;
import org.example.smartspring.dto.zone.ZoneDTO;
import org.example.smartspring.dto.clientexpediteur.ClientExpediteurDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColisDTO {

    private String statut;
    private String priorite;
    private ClientExpediteurDTO expediteur;
    private DestinataireDTO destinataire;
    private ZoneDTO zone;
    private List<ProduitDTO> produits;
    private String description;
    private Double poids;
}
