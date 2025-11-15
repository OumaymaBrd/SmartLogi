package org.example.smartspring.listeners;

import org.example.smartspring.entities.ClientExpediteur;
import org.example.smartspring.entities.Colis;
import org.example.smartspring.entities.Destinataire;
import org.example.smartspring.entities.Livreur;
import org.example.smartspring.events.ColisStatusChangeEvent;
import org.example.smartspring.repository.ColisRepository;
import org.example.smartspring.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ColisStatusChangeListenerTest {

    @InjectMocks
    private ColisStatusChangeListener listener;

    @Mock
    private EmailService emailService;
    @Mock
    private ColisRepository colisRepository;

    private final String COLIS_ID = "C001";
    private final String EXP_EMAIL = "exp@test.com";
    private final String DEST_EMAIL = "dest@test.com";

    private ColisStatusChangeEvent createEvent(String status) {
        return new ColisStatusChangeEvent(COLIS_ID, status);
    }

    private Colis createBaseColis() {
        Colis colis = new Colis();
        colis.setNumeroColis("COLIS-TEST");

        ClientExpediteur expediteur = new ClientExpediteur();
        expediteur.setEmail(EXP_EMAIL);
        colis.setClientExpediteur(expediteur);

        Destinataire destinataire = new Destinataire();
        destinataire.setEmail(DEST_EMAIL);
        colis.setDestinataire(destinataire);

        return colis;
    }

    @Test
    void onColisStatusChanged_ColisNotFound_ShouldDoNothing() {
        when(colisRepository.findById(COLIS_ID)).thenReturn(Optional.empty());
        ColisStatusChangeEvent event = createEvent("TRAITER");

        listener.onColisStatusChanged(event);

        verify(colisRepository, times(1)).findById(COLIS_ID);
        verifyNoInteractions(emailService);
    }

    @Test
    void onColisStatusChanged_StatusCollecte_WithLivreur_ShouldSendDetails() {
        Colis colis = createBaseColis();
        Livreur livreur = new Livreur();
        livreur.setNom("Dupont");
        livreur.setPrenom("Jean");
        livreur.setTelephone("0601020304");
        colis.setLivreur(livreur);

        when(colisRepository.findById(COLIS_ID)).thenReturn(Optional.of(colis));
        ColisStatusChangeEvent event = createEvent("collecte");

        String expectedBody = String.format(
                "Bonjour,\n\nVotre colis %s a été collecté par :\n- Nom : %s %s\n- Téléphone : %s\n\nCordialement.",
                colis.getNumeroColis(), livreur.getNom(), livreur.getPrenom(), livreur.getTelephone());

        // ACT
        listener.onColisStatusChanged(event);

        String expectedSubject = "Votre colis a été collecté";

        verify(emailService, times(1)).sendSimpleEmailAndLog(
                eq(EXP_EMAIL),
                eq(expectedSubject),
                eq(expectedBody),
                eq(COLIS_ID),
                eq("COLLECTE")
        );

        verify(emailService, times(1)).sendSimpleEmailAndLog(
                eq(DEST_EMAIL),
                eq(expectedSubject),
                eq(expectedBody),
                eq(COLIS_ID),
                eq("COLLECTE")
        );
    }

    @Test
    void onColisStatusChanged_UnknownStatus_ShouldDoNothing() {
        Colis colis = createBaseColis();
        when(colisRepository.findById(COLIS_ID)).thenReturn(Optional.of(colis));
        ColisStatusChangeEvent event = createEvent("STATUT_INCONNU");

        listener.onColisStatusChanged(event);

        verify(colisRepository, times(1)).findById(COLIS_ID);
        verifyNoInteractions(emailService);
    }


}