package team.haedal.gifticionfunding.controller.common.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ApiExceptionControllerAdvice {

    @ExceptionHandler
    public ErrorResponse illegalArgumentExceptionHandler(IllegalArgumentException e){
        log.info("IllegalArgumentException : {}", e.getMessage());
        return ErrorResponse.builder()
                .debugMessage(e.getMessage())
                .code(List.of("IllegalArgumentException"))
                .build();
    }

    @ExceptionHandler
    public ErrorResponse runtimeExceptionHandler(RuntimeException e){
        log.error("RuntimeException : {}", e.getMessage());
        return ErrorResponse.builder()
                .debugMessage(e.getMessage())
                .code(List.of("RuntimeException"))
                .build();
    }
}
