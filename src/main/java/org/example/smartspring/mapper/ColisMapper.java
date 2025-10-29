package org.example.smartspring.mapper;

import org.example.smartspring.dto.reponse.ColisResponseDTO;
import org.example.smartspring.dto.request.ColisRequestDTO;
import org.example.smartspring.entities.Colis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ColisMapper {

    // DTO --> Entity
    @Mapping(source = "idClientExpediteur", target = "clientExpediteur.id")
    @Mapping(source = "idDestinataire", target = "destinataire.id")
    @Mapping(source = "idZone", target = "zone.id")
    Colis toEntity(ColisRequestDTO dto);

    // Entity --> DTO
    ColisResponseDTO toResponseDTO(Colis entity);
}
