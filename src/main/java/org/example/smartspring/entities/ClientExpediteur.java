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

    @Id
    private String id;

    private String nom;
}
