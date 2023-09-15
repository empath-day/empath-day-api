package com.empathday.empathdayapi.interfaces.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDto {

    private String email;
    private String password;
    private String nickname;
    private int age;
    private String city;
}
