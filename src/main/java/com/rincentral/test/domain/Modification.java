package com.rincentral.test.domain;

import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Modification {

    @Id
    private Long id;

    private String title;

    @OneToOne
    private Generation generation;

    @Column(name = "fuel_type")
    private FuelType fuelType;

    @Column(name = "engine_type")
    private EngineType engineType;

    @Column(name = "engine_displacement")
    private Integer engineDisplacement;

    @Column(name = "gearbox_type")
    private GearboxType gearboxType;

    @Column(name = "wheeldrive_type")
    private WheelDriveType wheelDriveType;

    @Column(name = "body_style")
    private String bodyStyle;

    private Double acceleration;

    private Integer hp;

    @Column(name = "max_speed")
    private Integer maxSpeed;
}
