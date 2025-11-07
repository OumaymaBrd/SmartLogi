package org.example.smartspring.dto.colis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColisFilterDTO {
    private StatutColis statut;
    private PrioriteColis priorite;
    private String zoneNom;
    private String ville;
    private LocalDateTime dateCreationDebut;
    private LocalDateTime dateCreationFin;
    private String livreurId;
    private String motCle;

    // Pagination
    private Integer page = 0;
    private Integer size = 20;
    private String sortBy = "dateCreation";
    private String sortDirection = "DESC";
}
