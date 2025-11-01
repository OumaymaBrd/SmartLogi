package org.example.smartspring.mapper;

import org.example.smartspring.dto.ClientExpediteur.AddClientExpediteurDTO;
import org.example.smartspring.dto.ClientExpediteur.ClientExpediteurDTO;
import org.example.smartspring.dto.ClientExpediteur.UpdateClientExpediteurDTO;
import org.example.smartspring.entities.ClientExpediteur;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientExpediteurMapper {

    ClientExpediteur toEntity(AddClientExpediteurDTO dto);

    ClientExpediteurDTO toDto(ClientExpediteur entity);

    void updateEntityFromDto(UpdateClientExpediteurDTO dto, @MappingTarget ClientExpediteur entity);
}
