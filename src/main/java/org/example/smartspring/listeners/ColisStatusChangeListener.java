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

        switch (statut) {

            case "TRAITER", "TRAITÉ", "TRAITE": {
                String subject = "Votre colis est en cours de traitement";
                String body = "Bonjour,\n\nVotre colis " + colis.getNumeroColis() + " est maintenant en cours de traitement.\n\nCordialement.";
                emailService.sendSimpleEmailAndLog(emailExpediteur, subject, body, colisId, statut);
                break;
            }

            case "COLLECTE": {
                String subject = "Votre colis a été collecté";
                String body;

                if (colis.getLivreur() != null) {
                    Livreur l = colis.getLivreur();
                    body = String.format("""
                            Bonjour,

                            Votre colis %s a été collecté par :
                            - Nom : %s %s
                            - Téléphone : %s

                            Cordialement.""",
                            colis.getNumeroColis(), l.getNom(), l.getPrenom(), l.getTelephone());
                } else {
                    body = "Bonjour,\n\nVotre colis " + colis.getNumeroColis() + " a été collecté.\n\nCordialement.";
                }

                emailService.sendSimpleEmailAndLog(emailExpediteur, subject, body, colisId, statut);
                break;
            }

            case "EN_STOCK": {
                String subject = "Votre colis est en stock";
                String body = "Bonjour,\n\nVotre colis " + colis.getNumeroColis() + " est actuellement en stock.\n\nCordialement.";
                emailService.sendSimpleEmailAndLog(emailExpediteur, subject, body, colisId, statut);
                break;
            }

            case "EN_TRANSIT": {
                String subjectExp = "Votre colis est en transit";
                String bodyExp = "Bonjour,\n\nVotre colis " + colis.getNumeroColis() + " est en transit vers le destinataire.\n\nCordialement.";
                emailService.sendSimpleEmailAndLog(emailExpediteur, subjectExp, bodyExp, colisId, statut);

                // envoyer aussi au destinataire
                String subjectDest = "Votre colis est en route";
                String bodyDest = "Bonjour,\n\nUn colis (numéro : " + colis.getNumeroColis() + ") est en route vers vous.\n\nCordialement.";
                emailService.sendSimpleEmailAndLog(emailDestinataire, subjectDest, bodyDest, colisId, statut);
                break;
            }

            case "LIVRE": {
                String subject = "Votre colis a été livré";
                String body = "Bonjour,\n\nVotre colis " + colis.getNumeroColis() + " a été livré avec succès.\n\nCordialement.";
                emailService.sendSimpleEmailAndLog(emailExpediteur, subject, body, colisId, statut);

                String subjectDest = "Colis livré";
                String bodyDest = "Bonjour,\n\nLe colis " + colis.getNumeroColis() + " a été livré. Merci pour votre confiance.";
                emailService.sendSimpleEmailAndLog(emailDestinataire, subjectDest, bodyDest, colisId, statut);
                break;
            }

            default:
                // Aucun envoi mail si statut non concerné
                break;
        }
    }
}
