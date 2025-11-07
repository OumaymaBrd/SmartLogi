package org.example.smartspring.mapper;

import org.example.smartspring.dto.livreur.AddLivreurDTO;
import org.example.smartspring.dto.livreur.ConsulterColisAffecterDTO;
import org.example.smartspring.dto.livreur.LivreurDTO;
import org.example.smartspring.dto.livreur.UpdateLivreurDTO;
import org.example.smartspring.entities.Livreur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ZoneMapper.class})
public interface LivreurMapper {

    @Mapping(target = "zone", ignore = true)
    Livreur toEntity(AddLivreurDTO dto);

    @Mapping(target = "zone", source = "zone")
    LivreurDTO toDto(Livreur entity);

    @Mapping(target = "zone", ignore = true)
    void updateEntityFromDto(UpdateLivreurDTO dto, @MappingTarget Livreur entity);

}
