package org.example.smartspring.service;

import org.example.smartspring.dto.produit.AddProduitDTO;
import org.example.smartspring.dto.produit.ProduitDTO;
import org.example.smartspring.dto.produit.UpdateProduitDTO;
import org.example.smartspring.entities.Produit;
import org.example.smartspring.exception.ResourceNotFoundException;
import org.example.smartspring.mapper.ProduitMapper;
import org.example.smartspring.repository.ProduitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProduitServiceTest {

    @Mock
    private ProduitRepository repository;

    @Mock
    private ProduitMapper mapper;

    @InjectMocks
    private ProduitService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createProduit_shouldReturnProduitDTO_whenSuccess() {
        AddProduitDTO dto = new AddProduitDTO(
                "Produit1",
                "Description1",
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(5)
        );

        Produit entity = new Produit();
        entity.setNom(dto.getNom());
        entity.setDescription(dto.getDescription());
        entity.setPrixUnitaire(dto.getPrix());
        entity.setPrixUnitaire(dto.getPoids());

        Produit saved = new Produit();
        saved.setId("P1");
        saved.setNom(dto.getNom());
        saved.setDescription(dto.getDescription());
        saved.setPrixUnitaire(dto.getPrix());
        saved.setPrixUnitaire(dto.getPoids());

        ProduitDTO produitDTO = new ProduitDTO();
        produitDTO.setNom(dto.getNom());
        produitDTO.setDescription(dto.getDescription());
        produitDTO.setQuantite(0);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(produitDTO);

        ProduitDTO result = service.createProduit(dto);

        assertNotNull(result);
        assertEquals("Produit1", result.getNom());
        verify(repository).save(entity);
    }


    @Test
    void getProduitById_shouldReturnProduitDTO_whenFound() {
        String id = "P1";
        Produit entity = new Produit();
        entity.setId(id);

        ProduitDTO dto = new ProduitDTO();
        dto.setNom("Produit1");

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        ProduitDTO result = service.getProduitById(id);

        assertNotNull(result);
        assertEquals("Produit1", result.getNom());
    }

    @Test
    void getProduitById_shouldThrow_whenNotFound() {
        String id = "P2";

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getProduitById(id));
    }


    @Test
    void updateProduit_shouldReturnUpdatedDTO_whenFound() {
        String id = "P1";
        UpdateProduitDTO dto = new UpdateProduitDTO(
                "ProduitModifie",
                "DescModif",
                BigDecimal.valueOf(150),
                BigDecimal.valueOf(10)
        );

        Produit entity = new Produit();
        entity.setId(id);

        Produit updated = new Produit();
        updated.setId(id);
        updated.setNom(dto.getNom());

        ProduitDTO updatedDTO = new ProduitDTO();
        updatedDTO.setNom(dto.getNom());

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        doAnswer(invocation -> {
            entity.setNom(dto.getNom());
            entity.setDescription(dto.getDescription());
            entity.setPrixUnitaire(dto.getPrix());
            entity.setPrixUnitaire(dto.getPoids());
            return null;
        }).when(mapper).updateEntityFromDto(dto, entity);

        when(repository.save(entity)).thenReturn(updated);
        when(mapper.toDto(updated)).thenReturn(updatedDTO);

        ProduitDTO result = service.updateProduit(id, dto);

        assertNotNull(result);
        assertEquals("ProduitModifie", result.getNom());
        verify(repository).save(entity);
    }

    @Test
    void updateProduit_shouldThrow_whenNotFound() {
        String id = "P99";
        UpdateProduitDTO dto = new UpdateProduitDTO();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.updateProduit(id, dto));
    }

    @Test
    void deleteProduit_shouldCallRepositoryDelete_whenExists() {
        String id = "P1";

        when(repository.existsById(id)).thenReturn(true);

        service.deleteProduit(id);

        verify(repository).deleteById(id);
    }

    @Test
    void deleteProduit_shouldThrow_whenNotExists() {
        String id = "P2";

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deleteProduit(id));
        verify(repository, never()).deleteById(anyString());
    }


    @Test
    void getAllProduits_shouldReturnPage() {
        Produit p1 = new Produit();
        p1.setId("P1");
        ProduitDTO dto1 = new ProduitDTO();
        dto1.setNom("Produit1");

        List<Produit> list = List.of(p1);
        Page<Produit> page = new PageImpl<>(list);

        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(p1)).thenReturn(dto1);

        Page<ProduitDTO> result = service.getAllProduits(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Produit1", result.getContent().get(0).getNom());
    }
}
