package com.thienhoa.blog.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private Boolean success;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object content;

    public ApiResponse(){}

    public ApiResponse(Boolean success, String message, Object content){
        this.success = success;
        this.message = message;
        this.content = content;
    }

    public ApiResponse(Boolean success, String message){
        this.success = success;
        this.message = message;
    }
}
