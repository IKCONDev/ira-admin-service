package com.ikn.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ikn.admin.dto.userDto;

@Service
public interface userService  {

	userDto saveUser(userDto userDTO);

	 List<userDto> getAllUser() ;

	userDto updateUser(String email, userDto userDTO) ;

	 boolean deleteUser(String email) ;

	boolean authenticateUser(String email, String password);

     boolean forgotuser(String email);

	void setNewPassword(String email, String newPassword) ;

 boolean verifyOtp(String email, String otp) ;

 boolean generateAndSetOtpForUser(String email) ;
 
 userDto getUser (userDto userDto);



 
}

	
	
	
		
	
		
	



