package com.rincentral.test.controllers;

import com.rincentral.test.exception.IllegalRequestParameterValue;
import com.rincentral.test.exception.MissingRequireParameterException;
import com.rincentral.test.exception.TooManyParametersException;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.services.CarInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final CarInfoService carInfoService;

    @GetMapping("/cars")
    public ResponseEntity<List<? extends CarInfo>> getCars(CarRequestParameters requestParameters) {

        validateCarRequestParameters(requestParameters);

        List<? extends CarInfo> list;
        if (Boolean.TRUE.equals(requestParameters.getIsFull())) {
            list = carInfoService.getCarFullInfos(requestParameters);
        } else {
            list = carInfoService.getCarInfos(requestParameters);
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("/fuel-types")
    public ResponseEntity<List<String>> getFuelTypes() {
        List<String> fuelTypes = carInfoService.getFuelTypes();
        return ResponseEntity.ok(fuelTypes);
    }

    @GetMapping("/body-styles")
    public ResponseEntity<List<String>> getBodyStyles() {
        List<String> bodyStyles = carInfoService.getBodyStyles();
        return ResponseEntity.ok(bodyStyles);
    }

    @GetMapping("/engine-types")
    public ResponseEntity<List<String>> getEngineTypes() {
        List<String> types = carInfoService.getEngineTypes();
        return ResponseEntity.ok(types);
    }

    @GetMapping("/wheel-drives")
    public ResponseEntity<List<String>> getWheelDrives() {
        List<String> types = carInfoService.getWheelDriveTypes();
        return ResponseEntity.ok(types);
    }

    @GetMapping("/gearboxes")
    public ResponseEntity<List<String>> getGearboxTypes() {
        List<String> types = carInfoService.getGearboxes();
        return ResponseEntity.ok(types);
    }

    @GetMapping("/max-speed")
    public ResponseEntity<Double> getMaxSpeed(MaxSpeedRequestParameters requestParameters) {
        validateMaxSpeedRequest(requestParameters);
        Double average = carInfoService.getAverageMaxSpeed(requestParameters);
        return ResponseEntity.ok(average);
    }

    private void validateCarRequestParameters(CarRequestParameters params) {
        if (Objects.nonNull(params.getMinEngineDisplacement()) &&
                Double.compare(params.getMinEngineDisplacement(), 0.0) < 0) {
            throw new IllegalRequestParameterValue("'minEngineDisplacement' must be greater than 0");
        }

        if (Objects.nonNull(params.getMinEngineHorsepower()) && params.getMinEngineHorsepower() < 0) {
            throw new IllegalRequestParameterValue("'minEngineHorsepower' must be greater than 0");
        }

        if (Objects.nonNull(params.getMinMaxSpeed()) && params.getMinMaxSpeed() < 0) {
            throw new IllegalRequestParameterValue("'minMaxSpeed' must be greater than 0");
        }

        if (Objects.nonNull(params.getYear()) && params.getYear() < 0) {
            throw new IllegalRequestParameterValue("'year' must be greater than 0");
        }
    }

    private static void validateMaxSpeedRequest(MaxSpeedRequestParameters params) {
        if (StringUtils.isAllEmpty(params.getBrand(), params.getModel())) {
            throw new MissingRequireParameterException();
        } else if (StringUtils.isNotEmpty(params.getBrand()) && StringUtils.isNotEmpty(params.getModel())) {
            throw new TooManyParametersException();
        }
    }
}
