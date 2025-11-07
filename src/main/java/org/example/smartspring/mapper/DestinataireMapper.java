package org.example.smartspring.mapper;

import org.example.smartspring.dto.destinataire.DestinataireDTO;
import org.example.smartspring.entities.Destinataire;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DestinataireMapper {

    Destinataire toEntity(DestinataireDTO dto);

    DestinataireDTO toDTO(Destinataire entity);

    List<DestinataireDTO> toDTOList(List<Destinataire> entities);
}
