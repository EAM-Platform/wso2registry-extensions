package at.rbg.registry.model.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SvzConstraintValidator.class)
public @interface SvzConstraint
{ 

	String message() default "{at.rbg.registry.constraints.svzconstraint}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String value();
}
