package pl.malek.githubapirest.validators;

import static java.util.Objects.nonNull;

public abstract class Validator {
    private Validator nextValidator;

    public static Validator link(Validator first,
                                 Validator... chain) {
        Validator head = first;

        for (Validator nextInChain : chain) {
            head.nextValidator = nextInChain;
            head = nextInChain;
        }

        return first;
    }

    public abstract void validate(Object object);

    protected void validateNext(Object object) {
        if (nonNull(nextValidator)) {
            nextValidator.validate(object);
        }
    }
}
