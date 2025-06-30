package com.demo.service;

import com.demo.entity.Incident;
import com.demo.entity.IncidentStatus;
import com.demo.entity.User;
import com.demo.repository.IncidentRepository;
import com.demo.repository.UserRepository;
import com.demo.serviceresponse.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;
    @Autowired
    private UserRepository userRepository;

    public ServiceResponse<List<Incident>> createIncident(Long userId, Incident newIncident) {
        String uniqueIncidentId;
        do {
           uniqueIncidentId = generateIncidentId();
        } while(incidentRepository.findByIncidentId(uniqueIncidentId).isPresent());
        User user = (User) userRepository.findByUserId(userId);
        newIncident.setReportedAt(new Date());
        newIncident.setReporter((User) user);
        newIncident.setIncidentId(uniqueIncidentId);
        newIncident.setOrganizatonType(newIncident.getOrganizatonType());
        newIncident.setDescription(newIncident.getDescription());
        newIncident.setPriority(newIncident.getPriority()); //High, Medium, Low
        newIncident.setIncidentStatus(newIncident.getIncidentStatus()); //Open, In progress, Closed
        newIncident.setIncidentStatus(newIncident.getIncidentStatus());
        var createdIncident = incidentRepository.save(newIncident);
        ServiceResponse<List<Incident>> response = new ServiceResponse<>();
        response.setMessage("Incident Created Successfully !!");
        response.setData(List.of(createdIncident));
        return response;
    }

    private String generateIncidentId() {
        String prefix = "RMG";
        int randomNum = new Random().nextInt(90000) + 10000;
        int currentYear = LocalDate.now().getYear();
        return prefix + randomNum + currentYear;
    }

    public Incident getDetailsOfIncident(String incidentId) {
      Optional<Incident> details =  incidentRepository.findDetailsByIncidentId(incidentId);
      if(details.isEmpty()) {
          throw new ResourceNotFoundException("Incident Id not found");
      }
      return details.get();
    }


    public Incident updateIncidentDetails(Long userId, String incidentId, Incident updatedIncident) {
        Incident existingIncident = (Incident) incidentRepository.findByIncidentId(incidentId)
                .orElseThrow(() -> new RuntimeException("Incident not found"));
        if (!existingIncident.getUser().equals(userId)) {
            throw new RuntimeException("You are not allowed to edit this incident");
        }
        if (existingIncident.getIncidentStatus() == IncidentStatus.CLOSED) {
            throw new RuntimeException("Incident cannot be edited");
        }
        existingIncident.setOrganizatonType(updatedIncident.getOrganizatonType());
        existingIncident.setDescription(updatedIncident.getDescription());
        existingIncident.setPriority(updatedIncident.getPriority());
        existingIncident.setIncidentStatus(updatedIncident.getIncidentStatus());
        existingIncident.setReporter(updatedIncident.getReporter());
        existingIncident.setReportedAt(updatedIncident.getReportedAt());
        return incidentRepository.save(existingIncident);
    }
}
