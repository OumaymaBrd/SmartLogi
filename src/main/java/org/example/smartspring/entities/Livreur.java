package org.example.smartspring.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "livreur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livreur {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
}
