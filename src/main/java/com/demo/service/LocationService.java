package com.demo.service;

import com.demo.dto.LocationOffice;
import com.demo.dto.LocationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LocationService {

    public LocationResponse getCityAndCountry(String pincode) {
        String apiUrl = "https://api.postalpincode.in/pincode/" + pincode;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LocationResponse[]> response = restTemplate.getForEntity(apiUrl, LocationResponse[].class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().length > 0) {
           List<LocationOffice> postoffices = response.getBody()[0].getLocationOffice();
           if(postoffices != null && !postoffices.isEmpty()) {
               LocationResponse area = new LocationResponse();
               area.setStatus("OK");
               area.setLocationOffice(postoffices);
               return area;
           }
        }
        throw new RuntimeException("Invalid Pin Code");
    }
}