package team.haedal.gifticionfunding.controller.common.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.haedal.gifticionfunding.core.exception.ResourceNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApiExceptionControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalArgumentExceptionHandler(IllegalArgumentException e){
        log.info("IllegalArgumentException : {}", e.getMessage());
        return ErrorResponse.builder()
                .debugMessage(e.getMessage())
                .code(List.of("IllegalArgumentException"))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        return ErrorResponse.builder()
                .debugMessage("MethodArgumentNotValidException")
                .code(List.of(fieldErrors.stream().map(FieldError::getField).toArray(String[]::new)))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotFoundExceptionHandler(ResourceNotFoundException e){
        log.info("ResourceNotFoundException : {}", e.getMessage());
        return ErrorResponse.builder()
                .debugMessage(e.getMessage())
                .code(List.of("ResourceNotFoundException"))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse runtimeExceptionHandler(RuntimeException e){
        log.error("RuntimeException : {}", e.getMessage());
        return ErrorResponse.builder()
                .debugMessage(e.getMessage())
                .code(List.of("RuntimeException"))
                .build();
    }
}
