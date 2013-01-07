package at.rbg.registry.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import at.rbg.registry.persistance.dao.KeyCodeDao;


/**
 * Enforces Validation of @SvzConstraints
 * 
 * 
 */
public class SvzConstraintValidator implements ConstraintValidator<SvzConstraint, String>
{

	private String svzKeyName;

	private static KeyCodeDao keyCodeDao;


	public void setKeyCodeDao(KeyCodeDao keyCodeDao)
	{
		SvzConstraintValidator.keyCodeDao = keyCodeDao;
	}

	@Override
	public boolean isValid(String keyCodeCode, ConstraintValidatorContext constraintContext)
	{
		if (keyCodeCode==null || keyCodeCode.isEmpty()) {
			return true;
		}
			
		Boolean result = keyCodeDao.isKeyCodeValid(svzKeyName, keyCodeCode);
		return result;

	}

	@Override
	public void initialize(SvzConstraint svzKeyName)
	{
		this.svzKeyName = svzKeyName.value();
	}

}
