package coveo.backend.challenge.model;

public class CoveoException extends RuntimeException {
    private int errorCode;
    private String customMessage;
    private String errorName;
    private String devMessage;

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getDevMessage() {
        return devMessage;
    }

    public void setDevMessage(String devMessage) {
        this.devMessage = devMessage;
    }

    public CoveoException(int errorCode, String customMessage, String errorName, String devMessage){
        this.errorCode = errorCode;
        this.customMessage = customMessage;
        this.errorName = errorName;
        this.devMessage = devMessage;
    }
}
