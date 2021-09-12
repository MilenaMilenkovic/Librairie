package constraints;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraint implements ConstraintValidator<Password, String> {
	private final String passwordRegex = "^(?=[\\S]*[a-zA-Z])(?=[\\S]*[0-9])[\\S]{8,}$"; 
	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {				
		Pattern p = Pattern.compile(passwordRegex);
		Matcher m = p.matcher(password);
				 
		return m.matches();
	}

}
