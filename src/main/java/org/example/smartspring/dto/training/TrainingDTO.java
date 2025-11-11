package org.example.smartspring.dto.training;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder

public class TrainingDTO {

    private String nom_complet_clientExpediteur;
    private String id_colis;


}
