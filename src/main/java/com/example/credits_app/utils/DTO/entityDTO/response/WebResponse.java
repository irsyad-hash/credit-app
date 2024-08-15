package com.example.credits_app.utils.DTO.entityDTO.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> {
    private String message;
    private String status;
    private T data;
}
