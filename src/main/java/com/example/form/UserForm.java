package com.example.form;

import com.example.constraint.NameConstraint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

@Getter
@Setter
public class UserForm {

    @NameConstraint
    private String username;

    @Past
    private Date birthDate;

    @NotBlank
    private String password;
}
