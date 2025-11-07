package org.example.smartspring.mapper;

import org.example.smartspring.dto.zone.ZoneDTO;
import org.example.smartspring.entities.Zone;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ZoneMapper {

    Zone toEntity(ZoneDTO dto);

    ZoneDTO toDTO(Zone entity);

    List<ZoneDTO> toDTOList(List<Zone> entities);
}
