package org.fanti.uploader.server.dto;

import java.io.Serializable;

/**
 *
 * @author ftk
 * @date 2018/8/25
 */

public class ResultDTO<T> implements Serializable {
    private boolean success;
    private String message;
    private String messageCode;


    private T data;

    public ResultDTO() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
