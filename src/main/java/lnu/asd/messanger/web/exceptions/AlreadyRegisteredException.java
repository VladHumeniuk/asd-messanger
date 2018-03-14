package lnu.asd.messanger.web.exceptions;

public class AlreadyRegisteredException extends Exception {

    private static String MESSAGE_FORMAT = "userName = %s";

    public AlreadyRegisteredException(String userName) {
        super(String.format(MESSAGE_FORMAT, userName));
    }
}
