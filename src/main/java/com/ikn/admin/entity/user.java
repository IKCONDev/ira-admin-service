package com.ikn.admin.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_tab")
public class user 
{
	
	@Id
	private String email;
	private String password;
	private boolean isActive;
    private  String otp;
    private String encryptedPassword;
}
