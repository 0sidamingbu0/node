package com.mydreamplus.smartdevice.domain.message;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/18
 * Time: 下午3:48
 * To change this template use File | Settings | File Templates.
 * WebSocket 服务返回消息体
 */
public class WebSocketMessageResponse {

    private String errorMessage;

    private String success;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "WebSocketMessageResponse{" +
                "success='" + success + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
