package lnu.asd.messanger.web.exceptions;

public class UserNotFoundException extends Exception {

    private static final String TEXT_FORMAT = "User is not found. Id = %d";

    public UserNotFoundException(long userId) {
        super(String.format(TEXT_FORMAT, userId));
    }
}
