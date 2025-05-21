package com.example.FiteClub.Security.UserPackage;

import lombok.Data;

@Data
public class UpdateUser  {
    private String username;
    private String newUsername;
    private String newPassword;
}