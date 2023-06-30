package com.example.project_machimo.admin.dto;

import lombok.*;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private int userId;
    private String uId;
    private String uPassword;
    private String uEmail;
    private String uName;
    private String uJumin;
    private String uPhone;
    private String uAddress;
    private String uNickname;
    private int uPoint;
    private Timestamp uCreatedAt;
    private Timestamp uUpdatedAt;
    private int uRole;
    private String uSignout;
}
