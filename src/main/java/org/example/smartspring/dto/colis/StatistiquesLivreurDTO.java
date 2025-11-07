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
public class StatistiquesLivreurDTO {
    private String livreurId;
    private String livreurNom;
    private Long nombreColis;
    private BigDecimal poidsTotal;
    private Map<String, Long> colisParZone;
}
