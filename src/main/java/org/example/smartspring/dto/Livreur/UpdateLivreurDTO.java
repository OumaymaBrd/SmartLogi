package org.example.smartspring.dto.Livreur;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLivreurDTO {

    @Size(max = 100)
    private String nom;

    @Size(max = 100)
    private String prenom;

    @Size(max = 20)
    private String telephone;

    @Size(max = 100)
    private String vehicule;

    private Long zoneAssigneeId;
}
