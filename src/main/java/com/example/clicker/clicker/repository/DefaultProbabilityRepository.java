package com.example.clicker.clicker.repository;

import com.example.clicker.clicker.repository.models.ProbabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DefaultProbabilityRepository extends JpaRepository<ProbabilityEntity, UUID> {
}
