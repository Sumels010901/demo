package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDTO {
    private String username;
    private Date userDOB;
    private String userpass;
}
