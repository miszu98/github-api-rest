package pl.malek.githubapirest.validators.validators;

import pl.malek.githubapirest.exceptions.ValidationException;
import pl.malek.githubapirest.validators.Validator;

import static io.micrometer.common.util.StringUtils.isBlank;
import static io.micrometer.common.util.StringUtils.isEmpty;
import static pl.malek.githubapirest.exceptions.ExceptionMessages.LOGIN_CANNOT_BE_EMPTY_OR_BLANK;

public class LoginNotEmptyAndNotBlankValidator extends Validator {

    @Override
    public void validate(Object object) {
        final String value = (String) object;

        if (isEmpty(value) || isBlank(value)) {
            throw new ValidationException(LOGIN_CANNOT_BE_EMPTY_OR_BLANK.getValue());
        }

        validateNext(object);
    }

}
