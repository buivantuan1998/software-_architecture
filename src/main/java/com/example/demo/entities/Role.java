package com.example.demo.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Role")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String roleName;
}
