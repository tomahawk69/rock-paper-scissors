package interview.nokia.test.web;

public class ResponseError {

    private final String errorCode;
    private final String errorMessage;

    public ResponseError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
