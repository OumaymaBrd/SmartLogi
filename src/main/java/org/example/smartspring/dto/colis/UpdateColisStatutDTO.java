package org.example.smartspring.dto.colis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.StatutColis;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateColisStatutDTO {
    private StatutColis statut;
}
