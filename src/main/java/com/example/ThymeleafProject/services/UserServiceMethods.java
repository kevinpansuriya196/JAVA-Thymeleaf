package com.example.ThymeleafProject.services;

import com.example.ThymeleafProject.entities.UserEntity;

public interface UserServiceMethods {

UserEntity getUserById(Integer id);
UserEntity updateUser(UserEntity userEntity);

}
