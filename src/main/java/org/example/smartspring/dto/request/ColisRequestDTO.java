package org.example.smartspring.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.PrioriteColis;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColisRequestDTO {

    @NotNull(message = "Votre description est Null")
    private String description;

    @NotNull(message = "Votre poids est Null")
    private Double poids;

    private String idLivreur;

    @NotNull(message = "Votre idClientExpediteur est Null ")
    private String idClientExpediteur;

    @NotNull (message = "Votre idDestinataire est Null")
    private String idDestinataire;

    @NotNull (message = "Votre idZone est Null")
    private String idZone;

    @NotNull(message = "Votre villeDestination est Null")
    private String villeDestination;

    @NotNull(message = "Votre priorite est Null")
    private PrioriteColis priorite;



//
//        ClientExpediteur expediteur = clientExpediteurRepository.findById(dto.getIdClientExpediteur())
//                .orElseThrow(() -> new RuntimeException("ClientExpediteur non trouvé"));
//
//        Colis colis = Colis.builder()
//                .description(dto.getDescription())
//                .poids(dto.getPoids())
//                .clientExpediteur(expediteur)
//                .idDestinataire(dto.getIdDestinataire())
//                .idZone(dto.getIdZone())
//                .villeDestination(dto.getVilleDestination())
//                .priorite(dto.getPriorite())
//                .build();
//
//        return colisRepository.save(colis);
//

}
