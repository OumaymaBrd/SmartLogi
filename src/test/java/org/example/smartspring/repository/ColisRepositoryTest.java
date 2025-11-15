package org.example.smartspring.repository;

import org.example.smartspring.entities.Colis;
import org.example.smartspring.entities.Livreur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ColisRepositoryTest {

    @Mock
    private ColisRepository colisRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    private Livreur createLivreur(String id) {
        return Livreur.builder()
                .id(id)
                .nom("Doe")
                .prenom("John")
                .telephone("0612345678")
                .email("john.doe@mail.com")
                .vehicule("Moto")
                .build();
    }


    private Colis createColis(String id, Livreur livreur) {
        return Colis.builder()
                .id(id)
                .livreur(livreur)
                .numeroColis("NUM-" + id)
                .numeroSuivi("SUIV-" + id)
                .build();
    }

    @Test
    void testFindByLivreurId_withColis() {
        Livreur livreur = createLivreur("livreur-123");
        Colis colis1 = createColis("c1", livreur);
        Colis colis2 = createColis("c2", livreur);

        when(colisRepository.findByLivreur_Id(livreur.getId()))
                .thenReturn(Arrays.asList(colis1, colis2));

        List<Colis> result = colisRepository.findByLivreur_Id(livreur.getId());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("c1", result.get(0).getId());
        assertEquals("c2", result.get(1).getId());
        verify(colisRepository, times(1)).findByLivreur_Id(livreur.getId());
    }

    @Test
    void testFindByLivreurId_noColis() {
        String livreurId = "unknown-id";
        when(colisRepository.findByLivreur_Id(livreurId))
                .thenReturn(Collections.emptyList());

        List<Colis> result = colisRepository.findByLivreur_Id(livreurId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(colisRepository, times(1)).findByLivreur_Id(livreurId);
    }

    @Test
    void testFindByLivreurId_exception() {
        String livreurId = "error-id";
        when(colisRepository.findByLivreur_Id(livreurId))
                .thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            colisRepository.findByLivreur_Id(livreurId);
        });

        assertEquals("Database error", exception.getMessage());
        verify(colisRepository, times(1)).findByLivreur_Id(livreurId);
    }
}
