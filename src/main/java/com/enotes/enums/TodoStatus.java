package com.enotes.enums;

public enum TodoStatus {
	Not_Started(1, "Not_Started"), In_Progress(2, "In_Progress"), Completed(3, "Completed");

	private Integer id;

	private String name;

	private TodoStatus(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
