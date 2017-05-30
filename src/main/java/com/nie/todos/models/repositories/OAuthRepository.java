package com.nie.todos.models.repositories;

import com.nie.todos.models.pojos.dtos.userDTO.googleDTO.UserGoogle;
import com.nie.todos.models.pojos.entities.User;

/**
 * Created by inna on 29.05.17.
 */
public interface OAuthRepository {
    String auth(UserGoogle user, String token);
    User getUserByToken(String token);
    boolean isExistUserWithToken(String token);
}
