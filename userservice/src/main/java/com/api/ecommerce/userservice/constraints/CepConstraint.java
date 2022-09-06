package com.api.ecommerce.userservice.constraints;

import javax.validation.*;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CepConstraint.CepConstraintValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CepConstraint {

    String message() default "Invalid Cep";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

     class CepConstraintValidator implements ConstraintValidator<CepConstraint, String> {

        @Override
        public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
            return s.matches("^\\d{5}-\\d{3}$");
        }
    }

}
