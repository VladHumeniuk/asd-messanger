package lnu.asd.messenger.web.exceptions;

public class UserNotFoundException extends Exception {

    private static final String ID_TEXT_FORMAT = "User is not found. Id = %d";
    private static final String NAME_TEXT_FORMAT = "User is not found. UserName = %s";

    public UserNotFoundException(long userId) {
        super(String.format(ID_TEXT_FORMAT, userId));
    }

    public UserNotFoundException(String userName) {
        super(String.format(NAME_TEXT_FORMAT, userName));
    }
}
