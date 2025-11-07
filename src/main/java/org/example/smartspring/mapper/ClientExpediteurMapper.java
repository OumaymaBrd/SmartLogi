package org.example.smartspring.mapper;

import org.example.smartspring.dto.clientexpediteur.ClientExpediteurDTO;
import org.example.smartspring.entities.ClientExpediteur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientExpediteurMapper {

    @Mapping(target = "colis", ignore = true)
    ClientExpediteur toEntity(ClientExpediteurDTO dto);

    ClientExpediteurDTO toDTO(ClientExpediteur entity);

    List<ClientExpediteurDTO> toDTOList(List<ClientExpediteur> entities);
}
