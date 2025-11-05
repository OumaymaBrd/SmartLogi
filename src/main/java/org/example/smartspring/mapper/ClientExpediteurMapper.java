package org.example.smartspring.mapper;

import org.example.smartspring.dto.clientexpediteur.AddClientExpediteurDTO;
import org.example.smartspring.dto.clientexpediteur.ClientExpediteurDTO;
import org.example.smartspring.dto.clientexpediteur.UpdateClientExpediteurDTO;
import org.example.smartspring.entities.ClientExpediteur;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientExpediteurMapper {
    ClientExpediteur toEntity(AddClientExpediteurDTO dto);
    ClientExpediteurDTO toDto(ClientExpediteur entity);
    void updateEntityFromDto(UpdateClientExpediteurDTO dto, @MappingTarget ClientExpediteur entity);
//    DTo ---> Entity
    ClientExpediteur toEntityThreeParametre(ClientExpediteurDTO dto);


}
