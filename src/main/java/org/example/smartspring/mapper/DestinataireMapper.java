package org.example.smartspring.mapper;

import org.example.smartspring.dto.destinataire.AddDestinataireDTO;
import org.example.smartspring.dto.destinataire.DestinataireDTO;
import org.example.smartspring.dto.destinataire.UpdateDestinataireDTO;
import org.example.smartspring.entities.Destinataire;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DestinataireMapper {
    Destinataire toEntity(AddDestinataireDTO dto);
    DestinataireDTO toDto(Destinataire entity);
    void updateEntityFromDto(UpdateDestinataireDTO dto, @MappingTarget Destinataire entity);
}
