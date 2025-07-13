package com.enotes.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subject extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Boolean isActive;

    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;
}
