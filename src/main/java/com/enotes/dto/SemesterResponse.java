package com.enotes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SemesterResponse {

    private Integer id;

    private String name;

    private Boolean isActive;

    private CourseResponse course;

}
