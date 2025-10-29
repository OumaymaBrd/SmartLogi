package org.example.smartspring.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.PrioriteColis;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColisResponseDTO {

    private String id;
    private String description;
    private Double poids;
    private String villeDestination;
    private PrioriteColis priorite;
}
