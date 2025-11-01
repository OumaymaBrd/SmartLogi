package org.example.smartspring.mapper;


import org.example.smartspring.dto.LivreurDTO.LivreurDTO;
import org.example.smartspring.entities.Livreur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LivreurMapper {

    Livreur toEntity(LivreurDTO dto);
    LivreurDTO toDto(Livreur entity);



}
