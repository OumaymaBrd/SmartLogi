package org.example.smartspring.dto.colis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatistiquesDTO {
    private Long totalColis;
    private Map<String, Long> colisParStatut;
    private Map<String, Long> colisParPriorite;
    private Map<String, Long> colisParZone;
    private Map<String, StatistiquesLivreurDTO> statistiquesParLivreur;
    private Long colisEnRetard;
    private Long colisPrioritaires;
}
