package com.example.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class LoginResponse implements Serializable {
    private Long id;
    private String email;
    private String role;
}
