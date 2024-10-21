package com.ikn.admin.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import com.ikn.admin.dto.userDto;
import com.ikn.admin.service.userService;
 
import lombok.extern.slf4j.Slf4j;
@CrossOrigin(origins = "http://localhost:4200") 
@RestController
@RequestMapping("/user")
@Slf4j
public class userController
{
     @Autowired
	private userService userServiceImpl;
     @PostMapping("/saveuser")
     public ResponseEntity<userDto> saveUser(@RequestBody userDto userDTO) {
         userDto savedUser = userServiceImpl.saveUser(userDTO);
         return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
     }

	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers(){
	   List<userDto> userList =  userServiceImpl.getAllUser();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}
	@PutMapping("/updateuser/{email}")
    public ResponseEntity<userDto> updateUser(@PathVariable String email, @RequestBody userDto userDTO) {
        userDto updatedUser = userServiceImpl.updateUser(email, userDTO);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else 
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/deleteuser/{email}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable String email) {
        log.info("deleteUser() entered with email: {}", email);
        // Call the service to delete the user by email
        boolean isDeleted = userServiceImpl.deleteUser(email);
        if (isDeleted) {
            log.info("deleteUser() executed successfully for email: {}", email);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } else {
            log.warn("deleteUser() failed for email: {} - User not found", email);
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }@GetMapping("/forgotuser/{email}")
   public ResponseEntity<Boolean>forgotuser(@PathVariable String email){
    	Boolean isValid =    userServiceImpl.forgotuser(email);
		return new ResponseEntity<>( isValid  ,HttpStatus.OK);
    }
//Inorder to generate otp as integer
// @PostMapping("/request-otp/{email}")
//       public String requestOtp(@PathVariable String email) {
//           Integer otp = userService.generateOtp();
//           userService.setOtpForUser(email, otp);
//            return "OTP sent to your email."+otp;
//       }

		    @GetMapping("/request-otp/{email}")
		    public ResponseEntity<Boolean> requestOtp(@PathVariable (name="email") String email) {
		        boolean success = userServiceImpl.generateAndSetOtpForUser(email);
		        return  new ResponseEntity<>(success,HttpStatus.OK) ;
		    }
    @PostMapping("/verify-otp/{email}")
    public ResponseEntity<Boolean> verifyOtp(@PathVariable  (name="email") String email, @RequestParam  (name="otp") String otp)
 
    {
       Boolean isValidOtp;
    	if (userServiceImpl.verifyOtp(email, otp)) 
        {
    		isValidOtp=true;
            return new ResponseEntity<>(isValidOtp,HttpStatus.OK);
        } 
        else
        {
        	isValidOtp=false;
             return new ResponseEntity<>(isValidOtp,HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/reset-password")
    public ResponseEntity<Boolean> resetPassword(@RequestParam  (name="email")String email, @RequestParam (name="newPassword") String newPassword ) {
        if(email!=null )
        {
            userServiceImpl.setNewPassword(email, newPassword);
            return new ResponseEntity<>(true,HttpStatus.OK) ;
        } else {
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @PostMapping("/login")
	public ResponseEntity<userDto> getUser(@RequestBody userDto userdto){
		userDto user = userServiceImpl.getUser(userdto);
		return new ResponseEntity<>(userdto, HttpStatus.OK);
	}
 
}

    
