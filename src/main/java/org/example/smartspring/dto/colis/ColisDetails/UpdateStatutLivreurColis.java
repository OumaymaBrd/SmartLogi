package org.example.smartspring.dto.colis.ColisDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.StatutColis;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatutLivreurColis {
    private StatutColis statut;
}
