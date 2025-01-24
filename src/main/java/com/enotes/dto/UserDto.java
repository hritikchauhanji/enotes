package com.enotes.dto;

import java.util.List;

import com.enotes.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private Integer Id;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String mobNo;
	
	private List<RoleDto> roles;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class RoleDto{
		
		private Integer id;
		
		private String name;
	}
}
