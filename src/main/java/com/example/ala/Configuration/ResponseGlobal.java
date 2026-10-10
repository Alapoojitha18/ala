
        package com.example.ala.Configuration;

import lombok.Data;

@Data
public class ResponseGlobal<T> {

    private boolean success;
    private String message;
    private T data;

    public ResponseGlobal(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseGlobal<T> onSuccess(
            String message,
            T data) {

        return new ResponseGlobal<>(
                true,
                message,
                data
        );
    }

    public static <T> ResponseGlobal<T> onFailure(
            String message) {

        return new ResponseGlobal<>(
                false,
                message,
                null
        );
    }

    public static <T> ResponseGlobal<T> onError(
            String message) {

        return new ResponseGlobal<>(
                false,
                message,
                null
        );
    }
}
