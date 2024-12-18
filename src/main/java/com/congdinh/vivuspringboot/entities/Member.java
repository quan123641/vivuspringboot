package com.congdinh.vivuspringboot.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Column(name = "first_name", length = 50)
    private String firstname;
    @NotBlank
    @Column(name = "last_name", length = 50)
    private String lastname;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "password")
    private String password;

    @Column(name= "phone", length = 50)
    private String phone;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_time")
    private LocalDate updateTime;

    @Column(name = "description", nullable = true)
    private String description;

    @PrePersist
    protected void onCreate() {
        createDate = LocalDate.now();
        updateTime = LocalDate.now();
    }

     @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
     private List<Content> contents;

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDate.now();
    }
}