package org.example.smartspring.mapper;

import org.example.smartspring.dto.Produit.AddProduitDTO;
import org.example.smartspring.dto.Produit.ProduitDTO;
import org.example.smartspring.dto.Produit.UpdateProduitDTO;
import org.example.smartspring.entities.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProduitMapper {

    Produit toEntity(AddProduitDTO dto);

    ProduitDTO toDto(Produit entity);

    void updateEntityFromDto(UpdateProduitDTO dto, @MappingTarget Produit entity);
}
