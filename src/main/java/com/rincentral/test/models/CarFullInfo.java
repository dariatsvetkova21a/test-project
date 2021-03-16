package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarFullInfo extends CarInfo {

    @JsonProperty("engine")
    private CarEngine carEngine;

    @JsonProperty("body")
    private CarBody carBody;

    public CarFullInfo(CarInfo carInfo, CarEngine carEngine, CarBody carBody) {
        super(carInfo.getId(), carInfo.getSegment(), carInfo.getBrand(), carInfo.getModel(), carInfo.getCountry(), carInfo.getGeneration(), carInfo.getModification());
        this.carEngine = carEngine;
        this.carBody = carBody;
    }
}
