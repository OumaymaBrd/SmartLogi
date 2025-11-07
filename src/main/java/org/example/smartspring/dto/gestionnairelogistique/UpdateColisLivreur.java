package org.example.smartspring.dto.gestionnairelogistique;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.example.smartspring.enums.StatutColis;

@Data
public class UpdateColisLivreur {

    private String numero_colis;

    private StatutColis statut;

}
