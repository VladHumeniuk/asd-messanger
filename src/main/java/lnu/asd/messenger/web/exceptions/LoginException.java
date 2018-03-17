package lnu.asd.messenger.web.exceptions;

public class LoginException extends Exception {

    private static final String TEXT = "User does not exist or password is incorrect.";

    public LoginException() {
        super(TEXT);
    }
}
