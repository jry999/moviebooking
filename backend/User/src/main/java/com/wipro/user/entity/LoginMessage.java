package com.wipro.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginMessage {
	private String name;
	private String email;
    private String message;
    private boolean status;
    private String role;
}
