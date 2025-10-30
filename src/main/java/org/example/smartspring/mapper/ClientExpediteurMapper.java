package org.example.smartspring.mapper;


import org.example.smartspring.dto.CllientExpediteur.ClientExpediteurDTO;
import org.example.smartspring.entities.ClientExpediteur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientExpediteurMapper {

    ClientExpediteur toEntity(ClientExpediteurDTO dto);
    ClientExpediteurDTO toDto(ClientExpediteur entity);


}
