package com.ikn.admin.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userDto 
{
	private String email;
	private String password;
	private boolean isActive;
	private String otp;
	    private String encryptedPassword;

}
