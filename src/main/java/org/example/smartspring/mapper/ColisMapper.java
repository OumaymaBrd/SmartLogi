package org.example.smartspring.mapper;

import org.example.smartspring.dto.reponse.ColisResponseDTO;
import org.example.smartspring.dto.request.ColisRequestDTO;
import org.example.smartspring.entities.Colis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.swing.text.html.parser.Entity;


@Mapper(componentModel = "spring")

public interface ColisMapper {
//    DTO--->Entity
    @Mapping(source = "idClientExpediteur" , target = "ClientExpediteur.id")
    @Mapping(source = "id_destinataire" , target="Destinataire.id")
    @Mapping(source= "idZone" , target = "Zone.id")
    Colis toEntity(ColisRequestDTO dto);

//    Entity--->DTO

    ColisResponseDTO toResponseDTO(Colis entity);





}
