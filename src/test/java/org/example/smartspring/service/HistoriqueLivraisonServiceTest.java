package org.example.smartspring.service;

import org.example.smartspring.entities.HistoriqueLivraison;
import org.example.smartspring.repository.HistoriqueLivraisonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HistoriqueLivraisonServiceTest {

    @Mock
    private HistoriqueLivraisonRepository repository;

    @InjectMocks
    private HistoriqueLivraisonService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateDernierCommentaire_Success() {
        String colisId = "colis123";
        String nouveauCommentaire = "Livré avec succès";

        HistoriqueLivraison historique1 = HistoriqueLivraison.builder()
                .id("hist1")
                .colisId(colisId)
                .commentaire("Ancien commentaire")
                .dateChangement(LocalDateTime.now())
                .build();

        List<HistoriqueLivraison> historiques = Arrays.asList(historique1);

        when(repository.findByColisIdOrderByDateChangementDesc(colisId))
                .thenReturn(historiques);
        when(repository.save(any(HistoriqueLivraison.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        HistoriqueLivraison result = service.updateDernierCommentaire(colisId, nouveauCommentaire);

        assertEquals(nouveauCommentaire, result.getCommentaire());
        verify(repository, times(1)).save(historique1);
    }

    @Test
    void testUpdateDernierCommentaire_NotFound() {
        String colisId = "colis_inexistant";

        when(repository.findByColisIdOrderByDateChangementDesc(colisId))
                .thenReturn(Collections.emptyList());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.updateDernierCommentaire(colisId, "test")
        );

        assertEquals("404 NOT_FOUND \"Aucun historique trouvé pour ce colis : " + colisId + "\"",
                exception.getMessage());
        verify(repository, never()).save(any());
    }
}
