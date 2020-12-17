package com.thienhoa.blog.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private Boolean success;
    private String message;
    private Object content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;

    public ApiResponse(){
        this.createDate = new Date();
    }

    public ApiResponse(Boolean success, String message, Object content){
        this.success = success;
        this.message = message;
        this.content = content;
        this.createDate = new Date();
    }

    public ApiResponse(Boolean success, String message){
        this.success = success;
        this.message = message;
        this.createDate = new Date();
    }



}
