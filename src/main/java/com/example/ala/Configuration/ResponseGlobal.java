package com.example.ala.Configuration;

import lombok.Data;

@Data
//@RestControllerAdvice
//insdie Rest controller advice when i add it into my class i will properly handles my exception and i will return the proper response to the client.

public class ResponseGlobal<T> {

    private String message;
    private boolean success;
    //if it is true then success = true else false;
    private T data;

    //onFailure and onSuccess

    public static <T>  ResponseGlobal<T> onSuccess(String message, T data) {
        //success: true
        ResponseGlobal<T> response = new ResponseGlobal<>();
        response.setMessage(message);
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static<T> ResponseGlobal<T> onFailure(String message){
        ResponseGlobal<T> failure = new ResponseGlobal<>();
        failure.setMessage(message);
        failure.setSuccess(false);
        return failure;
    }

    public static <T>ResponseGlobal<T> onError(String message) {
        ResponseGlobal<T> error = new ResponseGlobal<>();
        error.setMessage(message);
        error.setSuccess(false);
        return error;
    }
}



//
//{
//        "Name":"Ritik",
//        "Age":23,
//        "Percentage":90.5,
//        "Status":"Active",
//        "message":"Student has been Created well."
//
//}
//

//
//{
//        "name": "Ritik new",
//        "age": 123,
//        "percentage": 123.12,
//        "id": 2
//        }












