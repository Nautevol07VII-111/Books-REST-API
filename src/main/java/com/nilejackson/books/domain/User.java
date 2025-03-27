package com.nilejackson.books.domain;

import java.time.LocalDate;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor

@Builder

@Table(name = "books")
public class User {
  private Long id;
  private String name;
  private String email;
  private String membershipId;
  private LocalDate signUpDate;  
  private boolean active;
}
