package org.example.smartspring.mapper;

import org.example.smartspring.dto.Zone.AddZoneDTO;
import org.example.smartspring.dto.Zone.UpdateZoneDTO;
import org.example.smartspring.dto.Zone.ZoneDTO;
import org.example.smartspring.entities.Zone;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ZoneMapper {

    Zone toEntity(AddZoneDTO dto);

    ZoneDTO toDto(Zone entity);

    void updateEntityFromDto(UpdateZoneDTO dto, @MappingTarget Zone entity);
}
