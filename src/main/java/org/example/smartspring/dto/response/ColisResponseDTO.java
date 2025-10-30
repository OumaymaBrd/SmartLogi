package org.example.smartspring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColisResponseDTO {
    private String id;
    private String description;
    private Double poids;
    private StatutColis statut;
    private PrioriteColis priorite;
    private String villeDestination;
    private String livreurNom;
    private String clientExpediteurNom;
    private String destinataireNom;
    private String zoneNom;
}
