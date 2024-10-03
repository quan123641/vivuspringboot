package com.congdinh.vivuspringboot.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "contents")
public class Content  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "brief is required")
    @Column(name = "brief")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")

    private String brief;

    @NotBlank(message = "content is required")
    @Column(name = "content")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")

    private String description;

    @Column(name = "create_local_date")
    private LocalDateTime createLocalDate;

    @Column(name = "up_local_date_time")
    private LocalDateTime upLocalDateTime;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "member_id", referencedColumnName = "id")
     private Member member;
    @PrePersist
    protected void onCreate() {
        if(this.createLocalDate == null) {
            this.createLocalDate = LocalDateTime.now();}
        this.upLocalDateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.upLocalDateTime = LocalDateTime.now();
    }
}
