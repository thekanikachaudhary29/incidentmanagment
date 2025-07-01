package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {

    @JsonProperty("Status")
    private String status;

    @JsonProperty("PostOffice")
    private List<LocationOffice> LocationOffice;
}