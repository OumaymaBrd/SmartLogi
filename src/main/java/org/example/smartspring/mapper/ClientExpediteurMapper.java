package org.example.smartspring.mapper;


import org.example.smartspring.dto.ClientExpediteur.ClientExpediteurDTO;
import org.example.smartspring.dto.ClientExpediteur.UpdateClientExpediteurDTO;
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
