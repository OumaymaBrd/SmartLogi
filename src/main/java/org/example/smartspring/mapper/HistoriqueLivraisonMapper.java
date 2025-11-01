package org.example.smartspring.mapper;

import org.example.smartspring.dto.historique.HistoriqueLivraisonDTO;
import org.example.smartspring.entities.HistoriqueLivraison;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HistoriqueLivraisonMapper {

    @Mapping(source = "colis.id", target = "colisId")
    HistoriqueLivraisonDTO toDto(HistoriqueLivraison entity);
}
