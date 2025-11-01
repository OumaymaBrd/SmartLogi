package org.example.smartspring.mapper;

import org.example.smartspring.dto.Destinataire.AddDestinataireDTO;
import org.example.smartspring.dto.Destinataire.DestinataireDTO;
import org.example.smartspring.dto.Destinataire.UpdateDestinataireDTO;
import org.example.smartspring.entities.Destinataire;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DestinataireMapper {

    Destinataire toEntity(AddDestinataireDTO dto);

    DestinataireDTO toDto(Destinataire entity);

    void updateEntityFromDto(UpdateDestinataireDTO dto, @MappingTarget Destinataire entity);
}
