package org.example.smartspring.mapper.ColisDeatilsMapper;
import org.example.smartspring.dto.colis.ColisDetails.ProduitDetailsDTO;
import org.example.smartspring.entities.ColisProduit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProduitDetailsMapper {

    @Mapping(target = "nom", source = "produit.nom")
    @Mapping(target = "prixUnitaire", source = "produit.prixUnitaire")
    ProduitDetailsDTO toDTO(ColisProduit colisProduit);
}

