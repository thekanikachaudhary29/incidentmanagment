package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationOffice {
    @JsonProperty("Region")
    private String region;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LocationOffice office = (LocationOffice) o;
        return Objects.equals(region, office.region) && Objects.equals(state, office.state) && Objects.equals(country, office.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, state, country);
    }

    @JsonProperty("State")
    private String state;

    @JsonProperty("Country")
    private String country;
}
