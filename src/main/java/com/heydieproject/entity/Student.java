package com.heydieproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data @Entity @AllArgsConstructor @NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String address;
    private String gender;
}
