package org.example.smartspring.mapper;

import org.example.smartspring.dto.colis.HistoriqueColisDTO;
import org.example.smartspring.entities.HistoriqueLivraison;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoriqueLivraisonMapper {

    @Mapping(source = "colis.id", target = "colisId")
    @Mapping(source = "gestionnaire.id", target = "gestionnaireId")
    @Mapping(source = "gestionnaire.nom", target = "gestionnaireNom")
    @Mapping(source = "gestionnaire.prenom", target = "gestionnairePrenom")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "statut", target = "statut")
    @Mapping(source = "commentaire", target = "commentaire")
    @Mapping(source = "dateChangement", target = "dateChangement")
    HistoriqueColisDTO toDTO(HistoriqueLivraison entity);

    List<HistoriqueColisDTO> toDTOList(List<HistoriqueLivraison> entities);
}
