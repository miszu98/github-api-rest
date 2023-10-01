package pl.malek.githubapirest.validators.processors;

import org.springframework.stereotype.Component;
import pl.malek.githubapirest.validators.Validator;
import pl.malek.githubapirest.validators.validators.UsernameNotEmptyAndNotBlankValidator;

@Component
public class UsernameValidationProcessor {

    private Validator validator;

    public UsernameValidationProcessor() {
        this.validator = Validator.link(
                new UsernameNotEmptyAndNotBlankValidator()
        );
    }

    public void validate(Object object) {
        this.validator.validate(object);
    }

}
