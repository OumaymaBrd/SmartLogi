package org.example.smartspring.dto.livreur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.PrioriteColis;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ConsulterColisAffecterDTO {

    private String numero_colis;
    private PrioriteColis priorite;
    private String client_expediteur_id;
    private String zone;
}
