package com.rincentral.test.services;

import com.rincentral.test.domain.Car;
import com.rincentral.test.domain.Modification;
import com.rincentral.test.models.CarBody;
import com.rincentral.test.models.CarEngine;
import com.rincentral.test.models.CarFullInfo;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.repository.CarDao;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.rincentral.test.services.SpecificationUtils.*;

@Service
@Transactional
public class CarInfoServiceImpl implements CarInfoService {

    private final CarDao carDao;
    private final EntityManager entityManager;

    public CarInfoServiceImpl(CarDao carDao, EntityManager entityManager) {
        this.carDao = carDao;
        this.entityManager = entityManager;
    }

    @Override
    public List<CarInfo> getCarInfos() {
        return carDao
                .findAll()
                .stream()
                .map(CarInfoServiceImpl::toCarInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarInfo> getCarInfos(CarRequestParameters carRequestParameters) {
        return findSpecifiedCars(carRequestParameters)
                .stream()
                .map(CarInfoServiceImpl::toCarInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarFullInfo> getCarFullInfos(CarRequestParameters carRequestParameters) {
        return findSpecifiedCars(carRequestParameters)
                .stream()
                .map(CarInfoServiceImpl::toCarFullInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFuelTypes() {
        List<FuelType> fuelTypes = entityManager
                .createQuery("select distinct c.modification.fuelType from Car c")
                .getResultList();

        return fuelTypes
                .stream()
                .map(FuelType::toString)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getBodyStyles() {
        List<String> bodyStyles = entityManager
                .createQuery("select distinct c.modification.bodyStyle from Car c")
                .getResultList();

        return bodyStyles
                .stream()
                .flatMap(style -> Arrays.stream(style.split("\\s*,\\s*")))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getEngineTypes() {
        List<EngineType> fuelTypes = entityManager
                .createQuery("select distinct c.modification.engineType from Car c")
                .getResultList();

        return fuelTypes
                .stream()
                .map(EngineType::toString)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getWheelDriveTypes() {
        List<WheelDriveType> fuelTypes = entityManager
                .createQuery("select distinct c.modification.wheelDriveType from Car c")
                .getResultList();

        return fuelTypes
                .stream()
                .map(WheelDriveType::toString)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getGearboxes() {
        List<GearboxType> fuelTypes = entityManager
                .createQuery("select distinct c.modification.gearboxType from Car c")
                .getResultList();

        return fuelTypes
                .stream()
                .map(GearboxType::toString)
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageMaxSpeed(MaxSpeedRequestParameters params) {

        List<Car> cars = carDao.findAll(
                Specification
                        .where(hasBrand(params.getBrand()))
                        .and(hasModel(params.getModel()))
        );

        return cars
                .stream()
                .mapToDouble(c -> c.getModification().getMaxSpeed())
                .average()
                .orElse(0.0);
    }

    private List<Car> findSpecifiedCars(CarRequestParameters parameters) {
        List<Car> cars = carDao.findAll(createSpecifications(parameters));
        return cars;
    }

    private static Specification<Car> createSpecifications(CarRequestParameters params) {
        return Specification
                .where(hasCountry(params.getCountry()))
                .and(hasSegment(params.getSegment()))
                .and(hasMinEngineDisplacement(params.getMinEngineDisplacement()))
                .and(hasMinEngineHorsepower(params.getMinEngineHorsepower()))
                .and(hasMinMaxSpeed(params.getMinMaxSpeed()))
                .and(hasSearch(params.getSearch()))
                .and(hasYear(params.getYear()))
                .and(hasBodyStyle(params.getBodyStyle()));
    }


    private static CarInfo toCarInfo(Car car) {
        return new CarInfo(
                Math.toIntExact(car.getId()),
                car.getModel().getSegment().getTitle(),
                car.getModel().getBrand().getTitle(),
                car.getModel().getTitle(),
                car.getModel().getBrand().getCountry().getTitle(),
                car.getModification().getGeneration().getTitle(),
                car.getModification().getTitle()
        );
    }

    private static CarFullInfo toCarFullInfo(Car car) {
        Modification modification = car.getModification();

        CarEngine carEngine = new CarEngine(
                modification.getFuelType(),
                modification.getEngineType(),
                modification.getEngineDisplacement(),
                modification.getHp());

        CarBody carBody = new CarBody(
                modification.getGeneration().getLength(),
                modification.getGeneration().getWidth(),
                modification.getGeneration().getHeight(),
                modification.getBodyStyle());

        return new CarFullInfo(toCarInfo(car), carEngine, carBody);
    }
}
