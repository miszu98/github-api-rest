package pl.malek.githubapirest.validators.processors;

import org.springframework.stereotype.Component;
import pl.malek.githubapirest.validators.Validator;
import pl.malek.githubapirest.validators.validators.LoginNotEmptyAndNotBlankValidator;

@Component
public class LoginValidationProcessor {

    private Validator validator;

    public LoginValidationProcessor() {
        this.validator = Validator.link(
                new LoginNotEmptyAndNotBlankValidator()
        );
    }

    public void validate(Object object) {
        this.validator.validate(object);
    }

}
