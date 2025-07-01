package com.demo.service;

import com.demo.dto.LocationOffice;
import com.demo.dto.LocationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    public LocationResponse getCityAndCountry(String pincode) {
        String apiUrl = "https://api.postalpincode.in/pincode/" + pincode;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LocationResponse[]> response = restTemplate.getForEntity(apiUrl, LocationResponse[].class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().length > 0) {
            LocationResponse[] allResponses = response.getBody();
            List<LocationOffice> postoffices = new ArrayList<>();
            for (LocationResponse res : allResponses) {
                List<LocationOffice> allOffices = res.getLocationOffice();
                if (allOffices != null && !allOffices.isEmpty()) {
                    postoffices.addAll(allOffices);
                }
            }
           List<LocationOffice> locationOffices = postoffices.stream().distinct().collect(Collectors.toList());
            if (!locationOffices.isEmpty()) {
                LocationResponse area = new LocationResponse();
                area.setStatus("OK");
                area.setLocationOffice(locationOffices);
                return area;
            }
        }
        throw new RuntimeException("Invalid Pin Code");
    }
}