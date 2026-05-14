package by.magofrays.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
    private final HttpStatus errorCode;
    public BusinessException(HttpStatus errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
