package org.example.smartspring.mapper;

import org.example.smartspring.dto.produit.ProduitDTO;
import org.example.smartspring.entities.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    Produit toEntity(ProduitDTO dto);

    ProduitDTO toDTO(Produit entity);

    List<ProduitDTO> toDTOList(List<Produit> entities);
}
