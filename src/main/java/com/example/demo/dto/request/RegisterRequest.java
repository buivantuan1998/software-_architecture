package com.example.demo.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String first_name;
    private String last_name;
    private String password;
}
