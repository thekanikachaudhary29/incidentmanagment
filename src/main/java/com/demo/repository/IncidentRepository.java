package com.demo.repository;

import com.demo.entity.*;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {


    Optional<Incident> findByIncidentId(String uniqueIncidentId);

@Query(value = """
            Select * from Incident i where i.incident_id = :incidentId
           """, nativeQuery = true)
Optional<Incident> findDetailsByIncidentId(@Param("incidentId") String incidentId);
}