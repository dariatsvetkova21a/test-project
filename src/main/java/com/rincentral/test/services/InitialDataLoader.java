package com.rincentral.test.services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rincentral.test.domain.Car;
import com.rincentral.test.models.external.ExternalCarInfo;
import com.rincentral.test.repository.*;
import lombok.Getter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class InitialDataLoader implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final CountryDao countryDao;
    private final BrandDao brandDao;
    private final GenerationDao generationDao;
    private final ModelDao modelDao;
    private final ModificationDao modificationDao;
    private final SegmentDao segmentDao;
    private final CarDao carDao;

    public InitialDataLoader(JdbcTemplate jdbcTemplate,
                             CountryDao countryDao,
                             BrandDao brandDao,
                             GenerationDao generationDao,
                             ModelDao modelDao,
                             ModificationDao modificationDao,
                             SegmentDao segmentDao,
                             CarDao carDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.countryDao = countryDao;
        this.brandDao = brandDao;
        this.generationDao = generationDao;
        this.modelDao = modelDao;
        this.modificationDao = modificationDao;
        this.segmentDao = segmentDao;
        this.carDao = carDao;
    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        TotalElements response = Optional
                .ofNullable(restTemplate.getForObject(
                        "http://localhost:8084/api/v1/cars/paged", TotalElements.class))
                .orElseThrow();

        IntStream
                .rangeClosed(1, response.getTotalElements())
                .forEach(i -> {
                    String url = "http://localhost:8084/api/v1/cars/" + i;
                    ExternalCarInfo info = restTemplate.getForObject(url, ExternalCarInfo.class);

                    var brand = brandDao
                            .findById(info.getBrandId().longValue())
                            .orElseThrow(() -> new IllegalStateException("Unexpected brand"));

                    var model = modelDao.findByTitle(info.getModel());

                    var modification = modificationDao
                            .findByTitleAndGenerationTitleAndGenerationModelTitle(
                                    info.getModification(),
                                    info.getGeneration(),
                                    info.getModel());

                    var car = new Car(0L, model, modification);
                    carDao.save(car);
                });
    }

    @Getter
    private static class TotalElements {

        private final int totalElements;

        @JsonCreator
        public TotalElements(
                @JsonProperty("totalElements") int totalElements) {
            this.totalElements = totalElements;
        }
    }
}
