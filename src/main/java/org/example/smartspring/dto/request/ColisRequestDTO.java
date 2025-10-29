package org.example.smartspring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import org.example.smartspring.enums.PrioriteColis;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColisRequestDTO {

    @NotNull
    private String description;

    @NotNull
    private Double poids;

    @org.jetbrains.annotations.NotNull
    private String idClientExpediteur;

    @NotNull
    private String idDestinataire;

    @NotNull
    private String idZone;

    private String villeDestination;

    private PrioriteColis priorite;
}
