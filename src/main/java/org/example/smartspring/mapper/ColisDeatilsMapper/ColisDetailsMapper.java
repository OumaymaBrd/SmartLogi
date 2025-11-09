package org.example.smartspring.mapper.ColisDeatilsMapper;

import org.example.smartspring.dto.colis.ColisDetails.ColisDetailsDTO;
import org.example.smartspring.entities.Colis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LivreurCollecteMapper.class, LivreurLivreeMapper.class, ProduitDetailsMapper.class})
public interface ColisDetailsMapper {

        @Mapping(source = "livreur", target = "livreurCollecte")
        @Mapping(source = "livreurLivree", target = "livreurLivree")
        @Mapping(source = "colisProduits", target = "produits")
        ColisDetailsDTO maptoDTO(Colis colis);
}
