package lnu.asd.messanger.web.utils;

import lnu.asd.messanger.web.entity.register.RegistrationError;

public class ErrorUtil {

    private static String FORMAT = "%s\n%s";

    public static RegistrationError getConflictError(String message) {
        RegistrationError error = new RegistrationError();
        error.setError(String.format(FORMAT, ErrorText.Registration.CONFLICT, message));
        return error;
    }
}
