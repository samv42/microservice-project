package co.codingnomads.spring.itemmicroservice;

import lombok.Getter;

@Getter
public class User {
    private Long id;

    private String username;

    private String password;

    private String email;

    private String fullName;
}
