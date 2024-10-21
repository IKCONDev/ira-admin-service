package com.ikn.admin.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ikn.admin.dto.userDto;
import com.ikn.admin.entity.user;
import com.ikn.admin.repository.userRepository;
import com.ikn.admin.service.userService;
import com.ikn.admin.utils.EmailService;
import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class userServiceImpl implements userService
{
	@Autowired
	private userRepository userRepository;
    
	@Autowired
    private ModelMapper modelMapper;
	
	 @Autowired
	 private EmailService emailService;

	@Override
	public userDto saveUser(userDto userDTO) {
	    log.info("saveUser() entered");
	    
	    // Convert DTO to entity using mapper
	    user user = new user();
	    modelMapper.map(userDTO, user);
	    
	    // Save user in the database
	    user savedUser = userRepository.save(user);
	    
	    // Convert saved entity back to DTO
	    userDto savedUserDTO = new userDto();
	    modelMapper.map(savedUser, savedUserDTO);
	    
	    log.info("saveUser() executed successfully");
	    return savedUserDTO;
	}

	@Override
	public List<userDto> getAllUser() {
	    log.info("getAllUser() entered");
	    List<user> users = userRepository.findAll();
	    List<userDto> userDtos = new ArrayList<>();
	    
	    // Convert each User entity to UserDto and add to the list
	    for (user user : users) {
	        userDto dto = new userDto();
	        modelMapper.map(user, dto);
	        
	        userDtos.add(dto);
	    }
	    
	    log.info("getAllUser() executed successfully with {} users", userDtos.size());
	    return userDtos;
	}

	@Override
	public userDto updateUser(String email, userDto userDTO) {
	    log.info("updateUser() entered with email: {}, userDto: {}", email, userDTO);
	    
	    // Fetch the existing user from the repository using the email
	    Optional<user> existingUser = userRepository.findByEmail(email);
	            //.orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
	    user user1 = new user();
        if(existingUser.isPresent()) {
          user1 = existingUser.get();	
        }
	    // Update fields from DTO to the existing entity
	    modelMapper.map(userDTO, existingUser);
	    
	    // Save the updated user
	    user savedUser = userRepository.save(user1);
	    
	    // Convert the updated entity to DTO
	    userDto savedUserDto = new userDto();
	    modelMapper.map(savedUser, savedUserDto);
	    
	    log.info("updateUser() executed successfully for email: {}", savedUserDto.getEmail());
	    return savedUserDto;
	}

	@Override
	public boolean deleteUser(String email) {
	    log.info("deleteUser() entered with email: {}", email);
	    
	    // Check if the user exists
	    Optional<user> user = userRepository.findByEmail(email);
	        //.orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
	    
	    // Delete the user....
	    user user1 = new user();
        if(user.isPresent()) {
          user1 = user.get();	
        }
	    userRepository.delete(user1);
	    log.info("deleteUser() executed successfully for email: {}", email);
		return true;
	}

	
	public boolean authenticateUser(String email, String password) {
	    // Fetch the user from the repository using the provided email
	    Optional<user> userOptional = userRepository.findByEmail(email);
	    user user1 = new user();

	    if (userOptional.isPresent()) {
	        user1 = userOptional.get();
	        System.out.println("User found: " + user1.getEmail());
	    } else {
	        System.out.println("User not found with email: " + email);
	        return false;
	    }

	    // Check if the user exists and validate the password
	    if (user1 != null) {
	        if (user1.getPassword().equals(password)) {
	            System.out.println("Password matches for user: " + user1.getEmail());
	            return true;
	        } else {
	            System.out.println("Password does not match for user: " + user1.getEmail());
	        }
	
	}
		return false;
}

	@Override  
	  public boolean forgotuser(String email)
	    {
	     if (email != null ) {
	    	  boolean userExists = userRepository.existsByEmail(email);
	            if (userExists) {
	    	           return true;
	            }  	
	     }
	     return false;
	    }

	
	  //Otp   generation
	public Integer generateOtp()
	{
	   Random r= new Random();
	   Integer otp = null ;
	int random = r.nextInt(899999)+100000;
		for (int i = 0; i < random; i++) {
			System.out.println("executed " + i);
			 otp = random;
			if (otp > 100000 && otp < 999999) {
			    return otp;
			}
	   }

		return otp;
	}
	
public boolean generateAndSetOtpForUser(String email) {
	    try {
	        Integer otp = generateOtp();
	        setOtpForUser(email, otp);
	       
	        return true;
	    } catch (Exception e) {
	      
	        return false;
	    }
	}
	


public void setOtpForUser(String email, Integer otp) {
    Optional<user> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isPresent()) {
        user user = optionalUser.get();
        user.setOtp(otp.toString());
        userRepository.save(user);
        String textBody = "Please find the OTP: " + otp;
        emailService.sendMail(email.toLowerCase(), "Two Factor Authentication", textBody, null, null, null, false);
    }
}

public void setNewPassword(String email, String newPassword) {
    Optional<user> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isPresent()) {
        user user = optionalUser.get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setEncryptedPassword(encoder.encode(newPassword));
        user.setOtp(null);
        userRepository.save(user);
    }
}

public boolean verifyOtp(String email, String otp) {
    Optional<user> optionalUser = userRepository.findByEmail(email);
    return optionalUser.isPresent() && optionalUser.get().getOtp().equals(otp);
}


@Override
public userDto getUser(userDto userDto) {
	Optional<user> user = userRepository.findByEmail(userDto.getEmail());
	modelMapper.map(user, userDto);
	return userDto;
}
}