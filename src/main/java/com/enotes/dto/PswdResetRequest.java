package com.enotes.dto;

import lombok.Data;

@Data
public class PswdResetRequest {

	private Integer uid;
	
	private String newPassword;
}
