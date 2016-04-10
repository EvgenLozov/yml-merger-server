package company.exception;

/**
 * @author Yevhen
 */
public class ErrorMessage {
    private Integer errorCode;
    private String message;

    public ErrorMessage(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
