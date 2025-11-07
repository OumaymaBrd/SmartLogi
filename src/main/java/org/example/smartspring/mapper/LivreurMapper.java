package org.example.smartspring.mapper;

import org.example.smartspring.dto.livreur.LivreurDTO;
import org.example.smartspring.entities.Livreur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LivreurMapper {

    @Mapping(target = "colis", ignore = true)
    Livreur toEntity(LivreurDTO dto);

    LivreurDTO toDTO(Livreur entity);

    List<LivreurDTO> toDTOList(List<Livreur> entities);
}
