package org.example.smartspring.mapper.ColisDeatilsMapper;

import org.example.smartspring.dto.colis.ColisDetails.LivreurCollecteDTO;
import org.example.smartspring.entities.Livreur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LivreurCollecteMapper {
    default LivreurCollecteDTO mapToDTO(Livreur livreur) {
        if (livreur == null) return null;
        return LivreurCollecteDTO.builder()
                .id(livreur.getId())
                .nom_complet(livreur.getNom() + " " + livreur.getPrenom())
                .build();
    }
}
