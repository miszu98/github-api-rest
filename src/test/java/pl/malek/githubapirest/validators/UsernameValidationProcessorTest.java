package pl.malek.githubapirest.validators;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.malek.githubapirest.exceptions.ExceptionMessages;
import pl.malek.githubapirest.exceptions.ValidationException;
import pl.malek.githubapirest.validators.processors.UsernameValidationProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.malek.githubapirest.exceptions.ExceptionMessages.USERNAME_CANNOT_BE_EMPTY_OR_BLANK;
import static pl.malek.githubapirest.utils.MockDataUtils.getFakeUsername;

@ExtendWith(MockitoExtension.class)
public class UsernameValidationProcessorTest {

    @InjectMocks
    private UsernameValidationProcessor usernameValidationProcessor;

    @Test
    void shouldPassAllValidators() {
        final String username = getFakeUsername();

        usernameValidationProcessor.validate(username);
    }

    @Test
    void shouldThrowExceptionUsernameIsEmptyOrBlankString() {
        final String username = " ";

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> usernameValidationProcessor.validate(username));

        assertEquals(USERNAME_CANNOT_BE_EMPTY_OR_BLANK.getValue(), validationException.getMessage());
    }
}
