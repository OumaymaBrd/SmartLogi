package org.example.smartspring.dto.livreur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.dto.zone.ZoneDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivreurDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private Boolean disponible;
    private ZoneDTO zone;
}
