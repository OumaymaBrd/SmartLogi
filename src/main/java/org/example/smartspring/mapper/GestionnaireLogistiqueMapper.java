package org.example.smartspring.mapper;

import org.example.smartspring.dto.gestionnaire.GestionnaireLogistiqueDTO;
import org.example.smartspring.dto.gestionnaire.GestionnaireResponseDTO;
import org.example.smartspring.entities.GestionnaireLogistique;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GestionnaireLogistiqueMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "actif", ignore = true)
    @Mapping(target = "historiques", ignore = true)
    GestionnaireLogistique toEntity(GestionnaireLogistiqueDTO dto);

    GestionnaireResponseDTO toResponseDTO(GestionnaireLogistique entity);
}
