package com.example.model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name="\"user\"")
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    private String username;

    private Date birthDate;

    private String password;
}
