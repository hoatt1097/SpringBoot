package com.thienhoa.blog.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ErrorResponse {
    private Boolean success;
    private String message;

    public ErrorResponse(String message){
        this.success = false;
        this.message = message;
    }
}
