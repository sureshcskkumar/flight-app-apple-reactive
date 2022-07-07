package com.flightapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
	private String email;
	private String password;
	private String role;
	private String firstName;
	private String lastName;

}
