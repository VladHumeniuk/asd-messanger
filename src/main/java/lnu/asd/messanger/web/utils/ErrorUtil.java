package lnu.asd.messanger.web.utils;

import lnu.asd.messanger.web.entity.register.RegistrationError;

public class ErrorUtil {

    public static RegistrationError getConflictError() {
        RegistrationError error = new RegistrationError();
        error.setError(ErrorText.Registration.CONFLICT);
        return error;
    }
}
