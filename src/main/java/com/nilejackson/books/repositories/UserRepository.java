package com.nilejackson.books.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nilejackson.books.domain.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
   Optional<UserEntity> findByEmail(String Email);
   Optional<UserEntity> findByMembershipId(String membershipId);
   List<UserEntity> findByActive(boolean active);
}
