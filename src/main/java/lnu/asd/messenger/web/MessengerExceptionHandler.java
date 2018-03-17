package lnu.asd.messenger.web;

import lnu.asd.messenger.web.entity.register.ServerErrorResponse;
import lnu.asd.messenger.web.exceptions.AlreadyRegisteredException;
import lnu.asd.messenger.web.exceptions.LoginException;
import lnu.asd.messenger.web.exceptions.UserNotFoundException;
import lnu.asd.messenger.web.utils.ErrorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MessengerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AlreadyRegisteredException.class)
    protected ResponseEntity<ServerErrorResponse> handleAlreadyRegisteredException(AlreadyRegisteredException ex) {
        return new ResponseEntity<>(ErrorUtil.getConflictError(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ServerErrorResponse> handleNotFoundException(UserNotFoundException ex) {
        ServerErrorResponse error = new ServerErrorResponse();
        error.setError(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternalError.class)
    protected ResponseEntity<ServerErrorResponse> handleInternalException(InternalError ex) {
        ServerErrorResponse error = new ServerErrorResponse();
        error.setError(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LoginException.class)
    protected ResponseEntity<ServerErrorResponse> handleLoginException(LoginException ex) {
        ServerErrorResponse error = new ServerErrorResponse();
        error.setError(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
