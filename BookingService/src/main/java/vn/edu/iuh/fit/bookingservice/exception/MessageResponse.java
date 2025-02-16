package vn.edu.iuh.fit.bookingservice.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class MessageResponse<T> implements java.io.Serializable {
    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("success")
    private boolean success;
    
    @JsonProperty("data")
    private T data;

    public MessageResponse(int statusCode, String message, boolean success) {
        this(statusCode, message, success, null);
    }

    public MessageResponse(int statusCode, String message, boolean success, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MessageResponse{" + "statusCode="
                + statusCode + ", message='"
                + message + '\'' + ", success="
                + success + ", data="
                + data + '}';
    }
}
