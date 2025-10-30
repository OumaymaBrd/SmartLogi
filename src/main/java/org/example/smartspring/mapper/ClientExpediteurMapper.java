package org.example.smartspring.mapper;


import org.example.smartspring.dto.CllientExpediteur.ClientExpediteurDTO;
import org.example.smartspring.dto.CllientExpediteur.UpdateClientExpediteurDTO;
import org.example.smartspring.entities.ClientExpediteur;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientExpediteurMapper {

    ClientExpediteur toEntity(ClientExpediteurDTO dto);
    ClientExpediteurDTO toDto(ClientExpediteur entity);

//    ClientExpediteur toUpdatedEntity(UpdateClientExpediteurDTO dto);

    void updateEntityFromDto(UpdateClientExpediteurDTO dto, @MappingTarget ClientExpediteur entity);


}
