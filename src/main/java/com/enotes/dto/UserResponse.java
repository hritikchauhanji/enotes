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
public class UserResponse {
	
	private Integer Id;

	private String firstName;

	private String lastName;

	private String email;

	private String mobNo;
	
	private StatusDto status;
	
	private List<RoleDto> roles;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class RoleDto{
		
		private Integer id;
		
		private String name;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class StatusDto{
		private Integer id;
		
		private Boolean isActive;
	}
}
