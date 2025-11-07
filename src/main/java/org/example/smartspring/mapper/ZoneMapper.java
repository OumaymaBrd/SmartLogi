package org.example.smartspring.mapper;

import org.example.smartspring.dto.zone.AddZoneDTO;
import org.example.smartspring.dto.zone.UpdateZoneDTO;
import org.example.smartspring.dto.zone.ZoneDTO;
import org.example.smartspring.entities.Zone;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ZoneMapper {
    Zone toEntity(AddZoneDTO dto);
    ZoneDTO toDto(Zone entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateZoneDTO dto, @MappingTarget Zone entity);
}
