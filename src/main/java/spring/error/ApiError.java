package spring.error;

public record ApiError(String error,
                       String message,
                       String status,
                       String timestamp) {
}