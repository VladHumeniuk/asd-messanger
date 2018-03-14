package lnu.asd.messanger.web;

import lnu.asd.messanger.web.exceptions.AlreadyRegisteredException;
import lnu.asd.messanger.web.utils.ErrorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MessangerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AlreadyRegisteredException.class)
    protected ResponseEntity<Object> handleAlreadyRegisteredException(AlreadyRegisteredException ex) {
        return new ResponseEntity<>(ErrorUtil.getConflictError(ex.getMessage()), HttpStatus.CONFLICT);
    }
}
