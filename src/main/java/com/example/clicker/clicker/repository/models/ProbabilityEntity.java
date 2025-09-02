package com.example.clicker.clicker.repository.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_probabilities")
public class ProbabilityEntity {

    @Id
    @SequenceGenerator(name = "u_prob_seq", sequenceName = "u_prob_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "u_prob_seq")
    @Column(name = "id")
    private UUID userId;

    @Column(name = "probability", nullable = false)
    private Float probability = 1F;

}
