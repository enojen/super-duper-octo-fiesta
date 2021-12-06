package com.enojen.getir.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponse<T> {
    private MessageResponse message;
    private T data;

    public BaseResponse(T data, MessageResponse message) {
        this.data = data;
        this.message = message;
    }

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(MessageResponse message) {
        this.message = message;
    }
}
