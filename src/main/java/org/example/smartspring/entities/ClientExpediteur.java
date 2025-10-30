package org.example.smartspring.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "client_expediteur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientExpediteur {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;

    private String telephone;
}
