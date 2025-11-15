package org.example.smartspring.service;

import org.example.smartspring.dto.gestionnairelogistique.AddGestionnaireLogistqueDTO;
import org.example.smartspring.dto.gestionnairelogistique.GestionnaireLogistiqueDTO;
import org.example.smartspring.entities.Colis;
import org.example.smartspring.entities.GestionnaireLogistique;
import org.example.smartspring.entities.Livreur;
import org.example.smartspring.enums.StatutColis;
import org.example.smartspring.mapper.GestionnaireLogistiqueMapper;
import org.example.smartspring.repository.ColisRepository;
import org.example.smartspring.repository.GestionnaireLogistiqueRepository;
import org.example.smartspring.repository.LivreurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestionnaireLogistiqueServiceTest {

    @Mock
    private GestionnaireLogistiqueRepository repository;

    @Mock
    private GestionnaireLogistiqueMapper mapper;

    @Mock
    private ColisRepository colisRepository;

    @Mock
    private LivreurRepository livreurRepository;

    @InjectMocks
    private GestionnaireLogistiqueService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void create_shouldReturnDto_whenValidDto() {
        AddGestionnaireLogistqueDTO dto = new AddGestionnaireLogistqueDTO();
        GestionnaireLogistique entity = new GestionnaireLogistique();
        GestionnaireLogistique saved = new GestionnaireLogistique();
        GestionnaireLogistiqueDTO dtoResult = new GestionnaireLogistiqueDTO();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dtoResult);

        GestionnaireLogistiqueDTO result = service.create(dto);

        assertNotNull(result);
        assertEquals(dtoResult, result);

        verify(repository).save(entity);
        verify(mapper).toDto(saved);
    }

    @Test
    void affecterLivreur_shouldThrow_whenGestionnaireNotFound() {
        when(repository.findById("gestionnaire1")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                service.affecterLivreur("COLIS1", "gestionnaire1", "livreur1", null)
        );

        assertEquals("404 NOT_FOUND \"Aucun gestionnaire trouvé avec l'id : gestionnaire1\"", ex.getMessage());
    }


    @Test
    void affecterLivreur_shouldThrow_whenColisNotFound() {
        GestionnaireLogistique g = new GestionnaireLogistique();
        when(repository.findById("gestionnaire1")).thenReturn(Optional.of(g));
        when(colisRepository.findByNumeroColis("COLIS1")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                service.affecterLivreur("COLIS1", "gestionnaire1", "livreur1", null)
        );

        assertEquals("404 NOT_FOUND \"Aucun colis trouvé avec numéro : COLIS1\"", ex.getMessage());
    }


    @Test
    void affecterLivreur_shouldThrow_whenLivreurNotFound() {
        GestionnaireLogistique g = new GestionnaireLogistique();
        Colis colis = new Colis();

        when(repository.findById("gestionnaire1")).thenReturn(Optional.of(g));
        when(colisRepository.findByNumeroColis("COLIS1")).thenReturn(Optional.of(colis));
        when(livreurRepository.findById("livreur1")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                service.affecterLivreur("COLIS1", "gestionnaire1", "livreur1", null)
        );

        assertEquals("404 NOT_FOUND \"Aucun livreur trouvé avec l'id : livreur1\"", ex.getMessage());
    }


    @Test
    void affecterLivreur_shouldAssignLivreurCollecteur_whenValid() {
        GestionnaireLogistique g = new GestionnaireLogistique();
        Colis colis = new Colis();
        Livreur livreur = new Livreur();
        livreur.setId("livreur1");

        when(repository.findById("gestionnaire1")).thenReturn(Optional.of(g));
        when(colisRepository.findByNumeroColis("COLIS1")).thenReturn(Optional.of(colis));
        when(livreurRepository.findById("livreur1")).thenReturn(Optional.of(livreur));

        String result = service.affecterLivreur("COLIS1", "gestionnaire1", "livreur1", null);

        assertEquals("Le colis avec le numéro COLIS1 a été affecté au livreur collecteur : livreur1", result);
        assertEquals(StatutColis.TRAITER, colis.getStatut());
        assertEquals(livreur, colis.getLivreur());

        verify(colisRepository).save(colis);
    }


    @Test
    void affecterLivreur_shouldAssignLivreurLivree_whenValid() {
        GestionnaireLogistique g = new GestionnaireLogistique();
        Colis colis = new Colis();
        Livreur livreur = new Livreur();
        livreur.setId("livreurLivree");

        when(repository.findById("gestionnaire1")).thenReturn(Optional.of(g));
        when(colisRepository.findByNumeroColis("COLIS1")).thenReturn(Optional.of(colis));
        when(livreurRepository.findById("livreurLivree")).thenReturn(Optional.of(livreur));

        String result = service.affecterLivreur("COLIS1", "gestionnaire1", null, "livreurLivree");

        assertEquals("Le colis avec le numéro COLIS1 a été affecté au livreur livré : livreurLivree", result);
        assertEquals(StatutColis.TRAITER, colis.getStatut());
        assertEquals(livreur, colis.getLivreurLivree());

        verify(colisRepository).save(colis);
    }

    @Test
    void affecterLivreur_shouldThrow_whenLivreurCollecteurAlreadyAssigned() {
        GestionnaireLogistique g = new GestionnaireLogistique();
        Colis colis = new Colis();
        colis.setLivreur(new Livreur());
        Livreur livreur = new Livreur();
        livreur.setId("livreur1");

        when(repository.findById("gestionnaire1")).thenReturn(Optional.of(g));
        when(colisRepository.findByNumeroColis("COLIS1")).thenReturn(Optional.of(colis));
        when(livreurRepository.findById("livreur1")).thenReturn(Optional.of(livreur));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                service.affecterLivreur("COLIS1", "gestionnaire1", "livreur1", null)
        );

        assertTrue(ex.getMessage().contains("Le livreur collecteur est déjà affecté"));
    }

    @Test
    void affecterLivreur_shouldThrow_whenLivreurLivreeAlreadyAssigned() {
        GestionnaireLogistique g = new GestionnaireLogistique();
        Colis colis = new Colis();
        colis.setLivreurLivree(new Livreur());
        Livreur livreur = new Livreur();
        livreur.setId("livreurLivree");

        when(repository.findById("gestionnaire1")).thenReturn(Optional.of(g));
        when(colisRepository.findByNumeroColis("COLIS1")).thenReturn(Optional.of(colis));
        when(livreurRepository.findById("livreurLivree")).thenReturn(Optional.of(livreur));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                service.affecterLivreur("COLIS1", "gestionnaire1", null, "livreurLivree")
        );

        assertTrue(ex.getMessage().contains("Le livreur livré est déjà affecté"));
    }
}
