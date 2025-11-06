package org.example.smartspring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client_expediteur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientExpediteur {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    private String email;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private String ville;

    @JsonIgnore
    @OneToMany(mappedBy = "clientExpediteur", cascade = CascadeType.ALL)
    private List<Colis> colis = new ArrayList<>();
}
