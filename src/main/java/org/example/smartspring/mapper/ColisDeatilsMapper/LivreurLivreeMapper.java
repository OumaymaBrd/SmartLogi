package org.example.smartspring.mapper.ColisDeatilsMapper;

import org.example.smartspring.dto.colis.ColisDetails.LivreurLivreeDTO;
import org.example.smartspring.entities.Colis;
import org.example.smartspring.entities.Livreur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LivreurLivreeMapper {
    default LivreurLivreeDTO maptoDTO(Livreur livreur) {
        if (livreur == null) return null;
        return LivreurLivreeDTO.builder()
                .id(livreur.getId())
                .nom_complet(livreur.getNom() + " " + livreur.getPrenom())
                .build();
    }
}