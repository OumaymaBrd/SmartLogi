package org.example.smartspring.mapper.traing;

import org.example.smartspring.dto.training.TrainingDTO;
import org.example.smartspring.entities.HistoriqueLivraison;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TraingMapper {

    @Mapping(target = "id_colis", source = "colisId")
    @Mapping(
            target = "nom_complet_clientExpediteur",
            expression = "java(helper.buildNom(entity.getColisId()))"
    )
    TrainingDTO mapToDTO(HistoriqueLivraison entity, @Context ClientExpediteurHelper helper);
}
