package org.example.smartspring.mapper.traing;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.entities.Colis;
import org.example.smartspring.entities.ClientExpediteur;
import org.example.smartspring.repository.ColisRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientExpediteurHelper {

    private final ColisRepository colisRepo;

    public String buildNom(String colisId) {
        return colisRepo.findById(colisId)
                .map(colis -> {
                    var client = colis.getClientExpediteur();
                    return client.getNom() + " " + client.getPrenom();
                })
                .orElse(null);
    }
}
