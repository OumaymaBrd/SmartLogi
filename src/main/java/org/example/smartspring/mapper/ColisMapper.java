package org.example.smartspring.mapper;

import org.example.smartspring.dto.colis.AddColisDTO;
import org.example.smartspring.dto.colis.ColisDTO;
import org.example.smartspring.dto.colis.UpdateColisDTO;
import org.example.smartspring.entities.Colis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ClientExpediteurMapper.class, DestinataireMapper.class, LivreurMapper.class, ZoneMapper.class})
public interface ColisMapper {

    @Mapping(target = "livreur", ignore = true)
    @Mapping(target = "clientExpediteur", ignore = true)
    @Mapping(target = "destinataire", ignore = true)
    @Mapping(target = "zone", ignore = true)
    Colis toEntity(AddColisDTO dto);

    ColisDTO toDto(Colis entity);

    @Mapping(target = "livreur", ignore = true)
    @Mapping(target = "clientExpediteur", ignore = true)
    @Mapping(target = "destinataire", ignore = true)
    @Mapping(target = "zone", ignore = true)
    void updateEntityFromDto(UpdateColisDTO dto, @MappingTarget Colis entity);
}
