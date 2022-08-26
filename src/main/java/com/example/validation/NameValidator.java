package com.example.validation;

import com.example.constraint.NameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<NameConstraint, String> {
    boolean allowNull;

    @Override
    public void initialize(NameConstraint nameConstraint) {
        allowNull = nameConstraint.allowNull();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext cxt) {
        if(name==null && allowNull){
            return true;
        }
        else return name!=null && !name.matches("[0-9]+") && (name.length() > 0) && (name.length() <20);
    }
}
