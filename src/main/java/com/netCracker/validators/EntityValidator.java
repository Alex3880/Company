package com.netCracker.validators;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by aleksandr on 16.02.17.
 */
public class EntityValidator<T> {

    public String validate(T obj) {
        Validator validator = null;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);

        String str = "";
        for (ConstraintViolation<T> tmp : constraintViolations) {
            str += tmp.getMessage() + "\n";
        }
        return str;
    }
}
