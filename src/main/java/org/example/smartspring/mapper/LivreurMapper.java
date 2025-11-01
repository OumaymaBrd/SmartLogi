package org.example.smartspring.mapper;

import org.example.smartspring.dto.Livreur.AddLivreurDTO;
import org.example.smartspring.dto.Livreur.LivreurDTO;
import org.example.smartspring.dto.Livreur.UpdateLivreurDTO;
import org.example.smartspring.entities.Livreur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LivreurMapper {

    @Mapping(target = "zoneAssignee.id", source = "zoneAssigneeId")
    Livreur toEntity(AddLivreurDTO dto);

    @Mapping(target = "zoneAssigneeId", source = "zoneAssignee.id")
    @Mapping(target = "zoneAssigneeNom", source = "zoneAssignee.nom")
    LivreurDTO toDto(Livreur entity);

    @Mapping(target = "zoneAssignee.id", source = "zoneAssigneeId")
    void updateEntityFromDto(UpdateLivreurDTO dto, @MappingTarget Livreur entity);
}
