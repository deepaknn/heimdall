package com.deepaknn.aop.heimdall.watcher.logger;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class LogDetails {
    public LogDetails() {
    }

    public LogDetails(String requestId, String methodName, String dataDirection, String data, String errorMessage, Timestamp createdAt, Long elapsedTime) {
        this.requestId = requestId;
        this.methodName = methodName;
        this.dataDirection = dataDirection;
        this.data = data;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
        this.elapsedTime = elapsedTime;
    }

    private String requestId;
    private String methodName;
    private String dataDirection;
    private String data;
    private String errorMessage;
    private Timestamp createdAt;
    private Long elapsedTime;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDataDirection() {
        return dataDirection;
    }

    public void setDataDirection(String dataDirection) {
        this.dataDirection = dataDirection;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    @Override
    public String toString() {
        return "LogDetails{" +
                "requestId='" + requestId + '\'' +
                ", methodName='" + methodName + '\'' +
                ", dataDirection='" + dataDirection + '\'' +
                ", data='" + data + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", createdAt=" + createdAt +
                ", elapsedTime=" + elapsedTime +
                '}';
    }
}
