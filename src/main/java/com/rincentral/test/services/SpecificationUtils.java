package com.rincentral.test.services;

import com.rincentral.test.domain.Car;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Objects;

final class SpecificationUtils {
    private SpecificationUtils() {
    }

    static Specification<Car> hasCountry(String country) {
        return (root, query, cb) ->
                StringUtils.isEmpty(country) ? null :
                        cb.equal(root.get("model").get("brand").get("country").get("title"), country);
    }

    static Specification<Car> hasSegment(String segment) {
        return (root, query, cb) ->
                StringUtils.isEmpty(segment) ? null :
                        cb.equal(root.get("model").get("segment").get("title"), segment);
    }

    static Specification<Car> hasMinEngineDisplacement(Double engineDisplacement) {
        return (root, query, cb) -> Objects.isNull(engineDisplacement) ? null :
                cb.greaterThanOrEqualTo(root.get("modification").get("engineDisplacement"), engineDisplacement);
    }

    static Specification<Car> hasMinEngineHorsepower(Integer minEngineHorsepower) {
        return (root, query, cb) -> Objects.isNull(minEngineHorsepower) ? null :
                cb.greaterThanOrEqualTo(root.get("modification").get("hp"), minEngineHorsepower);
    }

    static Specification<Car> hasMinMaxSpeed(Integer minMaxSpeed) {
        return (root, query, cb) -> Objects.isNull(minMaxSpeed) ? null :
                cb.greaterThanOrEqualTo(root.get("modification").get("maxSpeed"), minMaxSpeed);
    }

    static Specification<Car> hasSearch(String search) {
        return (root, query, cb) -> StringUtils.isEmpty(search) ? null :
                cb.or(
                        cb.equal(root.get("model").get("title"), search),
                        cb.equal(root.get("modification").get("generation").get("title"), search),
                        cb.equal(root.get("modification").get("title"), search));
    }

    static Specification<Car> hasBodyStyle(String bodyStyle) {
        return (root, query, cb) -> StringUtils.isEmpty(bodyStyle) ? null :
                cb.like(root.get("modification").get("bodyStyle"), StringUtils.join("%", bodyStyle, "%"));
    }

    static Specification<Car> hasBrand(String brandName) {
        return (root, query, cb) -> StringUtils.isEmpty(brandName) ? null :
                cb.equal(root.get("model").get("brand").get("title"), brandName);
    }

    static Specification<Car> hasModel(String modelName) {
        return (root, query, cb) -> StringUtils.isEmpty(modelName) ? null :
                cb.equal(root.get("model").get("title"), modelName);
    }


    static Specification<Car> hasYear(Integer year) {
        return (root, query, cb) -> Objects.isNull(year) ? null :
                createYearSpecification(root, query, cb, year);
    }

    private static Predicate createYearSpecification(
            Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder cb, Integer year) {

        Path<String> path = root.get("modification").get("generation").get("years");

        Expression<Integer> left = cb.substring(path, 0, 4).as(Integer.class);
        Expression<String> right = cb.substring(path, 6);
        Expression<Integer> rightCondition = cb
                .selectCase()
                .when(
                        cb.equal(right, "present"),
                        cb.function("YEAR", Integer.class, cb.currentDate()))
                .otherwise(right)
                .as(Integer.class);

        return cb.and(
                cb.lessThanOrEqualTo(left, year),
                cb.greaterThanOrEqualTo(rightCondition, year));
    }
}
