package org.training.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeConstraintValidator implements ConstraintValidator<AgeCheck, Integer> {
    private int minimalAge;

    @Override
    public void initialize(AgeCheck ageConstraint) {
        minimalAge = ageConstraint.minimalAge();
    }

    @Override
    public boolean isValid(Integer age, ConstraintValidatorContext constraintValidatorContext) {
        return age > minimalAge;
    }

    private void validateParameters()
    {
        if (minimalAge < 0)
        {
            throw new IllegalArgumentException("Minimal age can't be negative.");
        }
        if (minimalAge > 120)
        {
            throw new IllegalArgumentException("Minimal age should be smaller.");
        }
    }

}
