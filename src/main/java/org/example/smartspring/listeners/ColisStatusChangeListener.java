package org.example.smartspring.listeners;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.entities.Colis;
import org.example.smartspring.entities.Livreur;
import org.example.smartspring.events.ColisStatusChangeEvent;
import org.example.smartspring.repository.ColisRepository;
import org.example.smartspring.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ColisStatusChangeListener {

    private final EmailService emailService;
    private final ColisRepository colisRepository;

    @EventListener
    public void onColisStatusChanged(ColisStatusChangeEvent event) {

        String colisId = event.colisId();
        String statut = event.nouveauStatut().toUpperCase(); // Normalisation

        Optional<Colis> opt = colisRepository.findById(colisId);
        if (opt.isEmpty()) return;

        Colis colis = opt.get();

        String emailExpediteur = colis.getClientExpediteur() != null ? colis.getClientExpediteur().getEmail() : null;
        String emailDestinataire = colis.getDestinataire() != null ? colis.getDestinataire().getEmail() : null;

        // Définir le sujet et le corps du mail selon le statut
        String subject = "";
        String bodyExpediteur = "";
        String bodyDestinataire = "";

        switch (statut) {
            case "TRAITER", "TRAITÉ", "TRAITE":
                subject = "Votre colis est en cours de traitement";
                bodyExpediteur = "Bonjour,\n\nVotre colis " + colis.getNumeroColis() + " est maintenant en cours de traitement.\n\nCordialement.";
                bodyDestinataire = bodyExpediteur;
                break;

            case "COLLECTE":
                subject = "Votre colis a été collecté";
                if (colis.getLivreur() != null) {
                    Livreur l = colis.getLivreur();
                    bodyExpediteur = String.format(
                            "Bonjour,\n\nVotre colis %s a été collecté par :\n- Nom : %s %s\n- Téléphone : %s\n\nCordialement.",
                            colis.getNumeroColis(), l.getNom(), l.getPrenom(), l.getTelephone());
                } else {
                    bodyExpediteur = "Bonjour,\n\nVotre colis " + colis.getNumeroColis() + " a été collecté.\n\nCordialement.";
                }
                bodyDestinataire = bodyExpediteur;
                break;

            case "EN_STOCK":
                subject = "Votre colis est en stock";
                bodyExpediteur = "Bonjour,\n\nVotre colis " + colis.getNumeroColis() + " est actuellement en stock.\n\nCordialement.";
                bodyDestinataire = bodyExpediteur;
                break;

            case "EN_TRANSIT":
                subject = "Votre colis est en transit";
                bodyExpediteur = "Bonjour,\n\nVotre colis " + colis.getNumeroColis() + " est en transit vers le destinataire.\n\nCordialement.";
                bodyDestinataire = "Bonjour,\n\nUn colis (numéro : " + colis.getNumeroColis() + ") est en route vers vous.\n\nCordialement.";
                break;

            case "LIVRE":
                subject = "Votre colis a été livré";
                bodyExpediteur = "Bonjour,\n\nVotre colis " + colis.getNumeroColis() + " a été livré avec succès.\n\nCordialement.";
                bodyDestinataire = "Bonjour,\n\nLe colis " + colis.getNumeroColis() + " a été livré. Merci pour votre confiance.\n\nCordialement.";
                break;

            default:
                return; // Aucun email si statut non géré
        }

        // Envoi email à l'expéditeur si présent
        if (emailExpediteur != null && !emailExpediteur.isEmpty()) {
            emailService.sendSimpleEmailAndLog(emailExpediteur, subject, bodyExpediteur, colisId, statut);
        }

        // Envoi email au destinataire si présent
        if (emailDestinataire != null && !emailDestinataire.isEmpty()) {
            emailService.sendSimpleEmailAndLog(emailDestinataire, subject, bodyDestinataire, colisId, statut);
        }
    }
}
