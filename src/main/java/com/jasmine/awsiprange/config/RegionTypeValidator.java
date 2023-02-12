package com.jasmine.awsiprange.config;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RegionTypeValidator implements ConstraintValidator<RegionType, String> {


    @Override
    public boolean isValid(String region, ConstraintValidatorContext constraintValidatorContext) {
        if (region == null || region.isBlank()) {
            return false;
        }

        for (RegionEnum regionEnum : RegionEnum.values()) {
            if (regionEnum.name().equalsIgnoreCase(region)) {
                return true;
            }
        }

        return false;
    }


}
