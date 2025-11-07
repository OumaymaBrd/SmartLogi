package org.example.smartspring.dto.colis;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.dto.clientexpediteur.ClientExpediteurDTO;
import org.example.smartspring.dto.destinataire.DestinataireDTO;
import org.example.smartspring.dto.produit.ProduitDTO;
import org.example.smartspring.dto.zone.ZoneDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateColisDTO {

    private String statut;

    private String priorite;

    @NotNull(message = "L'expéditeur est obligatoire")
    @Valid
    private ClientExpediteurDTO expediteur;

    @NotNull(message = "Le destinataire est obligatoire")
    @Valid
    private DestinataireDTO destinataire;

    @NotNull(message = "La zone est obligatoire")
    @Valid
    private ZoneDTO zone;

    @NotEmpty(message = "Au moins un produit est obligatoire")
    @Valid
    private List<ProduitDTO> produits;

    private String description;

    private Double poids;
}
