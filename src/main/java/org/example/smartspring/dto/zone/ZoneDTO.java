package org.example.smartspring.dto.zone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDTO {

    private String id;
    private String nom;
    private String description;
}
