package com.jasmine.awsiprange.config;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RegionTypeValidator.class)
public @interface RegionType {

    String message() default "Region should be any values of EU, US, AP, CN, SA, AF, CA, ALL";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}


