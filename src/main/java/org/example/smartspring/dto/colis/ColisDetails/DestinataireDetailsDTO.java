package org.example.smartspring.dto.colis.ColisDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DestinataireDetailsDTO {
    private String nom_complet;
    private String email;
}
