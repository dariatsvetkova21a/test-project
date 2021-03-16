package com.rincentral.test.services;

import com.rincentral.test.models.CarFullInfo;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;

import java.util.List;

public interface CarInfoService {
    List<CarInfo> getCarInfos();

    List<CarInfo> getCarInfos(CarRequestParameters carRequestParameters);

    List<CarFullInfo> getCarFullInfos(CarRequestParameters carRequestParameters);

    List<String> getFuelTypes();

    List<String> getBodyStyles();

    List<String> getEngineTypes();

    List<String> getWheelDriveTypes();

    List<String> getGearboxes();

    Double getAverageMaxSpeed(MaxSpeedRequestParameters params);
}
