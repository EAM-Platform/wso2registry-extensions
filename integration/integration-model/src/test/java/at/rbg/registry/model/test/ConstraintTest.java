package at.rbg.registry.model.test;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import at.rbg.registry.model.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:META-INF/integration-model-Context.xml"})
public class ConstraintTest {
	
	@Test
	public void constraint() {
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		Validator validator = vf.getValidator();
	
		Application app = new Application();
		app.setName("GEOS");
		app.setVersion("1.0.1");
		app.setDomain("ORGAIT");
		
		Set<ConstraintViolation<Application>> cv2 = validator.validate(app);
		assertEquals(1, cv2.size());
		assertEquals("{at.rbg.registry.constraints.svzconstraint}", cv2.iterator().next().getMessage());

		app.setDomain("2");
		Set<ConstraintViolation<Application>> cv1 = validator.validate(app);
		assertEquals(0, cv1.size());

	}

}
