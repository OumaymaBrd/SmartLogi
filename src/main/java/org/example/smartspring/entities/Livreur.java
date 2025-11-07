package org.example.smartspring.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "livreur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livreur {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, length = 20)
    private String telephone;

    @Column(length = 50)
    private String vehicule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @Email
    private String email;
}
