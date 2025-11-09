package org.example.smartspring.mapper.ColisDeatilsMapper;

import org.example.smartspring.dto.colis.ColisDetails.ZoneDeatailsDTO;
import org.example.smartspring.entities.Zone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ZoneDeatailsMapper {


    ZoneDeatailsDTO mapToDto(Zone entity);
}
