package org.example.smartspring.dto.colis.ColisDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ZoneDeatailsDTO {
    private String nom;
    private String description;
}
