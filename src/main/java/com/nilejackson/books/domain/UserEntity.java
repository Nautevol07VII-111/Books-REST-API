package com.nilejackson.books.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Entity
@Table(name = "users")
@Builder
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String email;
    
    @Column(unique = true)
    private String membershipId;
    
    private LocalDate membershipStartDate;
    private LocalDate membershipExpiryDate;
    private boolean active;

    public boolean isMembershipValid() {
        return active && LocalDate.now().isBefore(membershipExpiryDate);
    }
}
