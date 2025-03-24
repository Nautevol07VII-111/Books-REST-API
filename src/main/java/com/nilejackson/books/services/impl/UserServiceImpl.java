package com.nilejackson.books.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nilejackson.books.domain.User;
import com.nilejackson.books.domain.UserEntity;
import com.nilejackson.books.repositories.UserRepository;
import com.nilejackson.books.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
         UserEntity userEntity = userToUserEntity(user);
        UserEntity savedEntity = userRepository.save(userEntity);
        return userEntityToUser(savedEntity);
            }
        
           
        
            @Override
    public Optional<User> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<User> findAllUsers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllUsers'");
    }

    @Override
    public User updateUser(Long id, User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public void deleteUser(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public boolean isActiveUser(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isActiveUser'");
    }

    private User userEntityToUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .membershipId(userEntity.getMembershipId())
                .signUpDate(userEntity.getSignUpDate())
                .active(userEntity.isActive())
                .build();
    }
    

    private UserEntity userToUserEntity(User user) {
    UserEntity entity = new UserEntity(null, null, null, null, null, false);
    if (user.getId() != null) {
        entity.setId(user.getId());
    }
    entity.setName(user.getName());
    entity.setEmail(user.getEmail());
    entity.setMembershipId(user.getMembershipId());
    entity.setSignUpDate(user.getSignUpDate());
    entity.setActive(user.isActive());
    return entity;
}
}
