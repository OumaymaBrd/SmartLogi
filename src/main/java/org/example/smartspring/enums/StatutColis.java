package org.example.smartspring.enums;

public enum StatutColis {
    CREE,           // Colis créé par le client expéditeur
    COLLECTE,       // Livreur affecté par le gestionnaire pour la collecte
    EN_STOCK,       // Colis collecté et mis en stock par le livreur
    EN_TRANSIT,     // Colis en transit vers le destinataire (après réaffectation)
    LIVRE,          // Colis livré au destinataire
    ANNULE ,
    ASSIGNE// Colis annulé
}
