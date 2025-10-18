package by.magofrays.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String code;
    private String message;
    private Object details;
    private LocalDateTime timestamp;
    public ErrorResponse(String code, String message) {
        this(code, message, null);
    }
    public ErrorResponse(String code, String message, Object details) {
        this.code = code;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

}
