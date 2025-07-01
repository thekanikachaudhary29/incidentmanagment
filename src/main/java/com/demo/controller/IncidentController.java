package com.demo.controller;

import com.demo.entity.Incident;
import com.demo.service.IncidentService;
import com.demo.serviceresponse.ServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incident")
@Tag(name = "Incident-Controller", description = "API for managing incidents")
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    //create incident
    @PostMapping("/{userId}/create")
    @Operation(summary = "Create new incident on the basis of userId", description = "Returns the incident details for the given incident ID")
    public ResponseEntity<ServiceResponse<List<Incident>>> createIncident(@PathVariable Long userId, @RequestBody Incident newIncident) {
        var incidents = incidentService.createIncident(userId, newIncident);
        return new ResponseEntity<>(incidents, HttpStatus.OK);
    }

    //search incident by incident id
    @GetMapping("/get/details")
    @Operation(summary = "Get details of incident based on incident Id, incident status can be OPEN | INPROGRESS | CLOSED, priority can be HIGH | MEDIUM | LOW", description = "Returns the incident details for the given incident ID")
    public ResponseEntity<Incident> getDetailsOfIncident(@RequestParam String incidentId) {
        Incident incidentDetails = incidentService.getDetailsOfIncident(incidentId);
        return new ResponseEntity<>(incidentDetails, HttpStatus.OK);
    }


    //A User should be allowed to view and edit the incidents created by
    //them only.
    @PutMapping("/{userId}/update/{incidentId}")
    @Operation(summary = "Update incident details", description = "Returns the incident details for the given incident ID")
    public ResponseEntity<ServiceResponse<Incident>> updateIncidentDetails(@PathVariable Long userId,
                                                          @PathVariable String incidentId,
                                                          @RequestBody Incident newIncident) {
        var incidents = incidentService.updateIncidentDetails(userId, incidentId, newIncident);
        return new ResponseEntity<>(incidents, HttpStatus.OK);
    }
}
