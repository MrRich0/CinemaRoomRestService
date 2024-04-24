package cinema.controller;

import cinema.exceptions.AlreadyPurchaseException;
import cinema.exceptions.BusinessException;
import cinema.exceptions.SeatCoordinatesOutBoundsException;
import cinema.exceptions.WrongPasswordException;
import cinema.models.ErrorDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class ErrorControllerAdvice {
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler
    ErrorDTO alreadyPurchaseHandler(BusinessException ex){
        return new ErrorDTO(ex.getMessage());
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler
    ErrorDTO wrongPasswordHandler(WrongPasswordException ex){
        return new ErrorDTO(ex.getMessage());
    }
}
