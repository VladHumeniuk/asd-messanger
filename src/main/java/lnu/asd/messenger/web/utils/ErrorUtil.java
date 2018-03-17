package lnu.asd.messenger.web.utils;

import lnu.asd.messenger.web.entity.register.ServerErrorResponse;

public class ErrorUtil {

    private static String FORMAT = "%s\n%s";

    public static ServerErrorResponse getConflictError(String message) {
        ServerErrorResponse error = new ServerErrorResponse();
        error.setError(String.format(FORMAT, ErrorText.Registration.CONFLICT, message));
        return error;
    }
}
