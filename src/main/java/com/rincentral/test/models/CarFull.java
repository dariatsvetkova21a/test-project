package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarFull {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("segment")
    private String segment;

    @JsonProperty("brand")
    private ExternalBrand brand;

    @JsonProperty("model")
    private String model;

    @JsonProperty("generation")
    private String generation;

    @JsonProperty("modification")
    private String modification;

    @JsonProperty("year_range")
    private String yearsRange;

    @JsonProperty("engine")
    private CarEngine carEngine;

    @JsonProperty("gearbox")
    private GearboxType gearboxType;

    @JsonProperty("wheel_drive")
    private WheelDriveType wheelDriveType;

    @JsonProperty("body")
    private CarBody carBody;

    @JsonProperty("acceleration")
    private Double acceleration;

    @JsonProperty("max_speed")
    private Integer maxSpeed;
}
