package com.ellianna.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BadRequest extends RuntimeException{
    private final Map<String, Object> error;

    public BadRequest(Map<String, Object> error){
        this.error = error;
    }

    public BadRequest(String error){
        Map<String,Object> errorMap = new HashMap<>();
        errorMap.put("Message", error);
        this.error = errorMap;
    }
}
