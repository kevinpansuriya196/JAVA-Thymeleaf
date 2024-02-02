package com.example.ThymeleafProject.services;

import com.example.ThymeleafProject.entities.UserEntity;
import com.example.ThymeleafProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceMethods {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserEntity> getAll() {
        return userRepository.findAll();

    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }


    public UserEntity getAllId(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }


    @Override
    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public void add(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public boolean isEmailUnique(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return user == null;
    }
}
