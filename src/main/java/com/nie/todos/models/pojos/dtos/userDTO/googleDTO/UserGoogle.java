package com.nie.todos.models.pojos.dtos.userDTO.googleDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by inna on 29.05.17.
 */
@Data
@AllArgsConstructor
public class UserGoogle {
    private String kind;
    private String etag;
    private String objectType;
    private String id;
    private String displayName;
    private UserNameGoogleDTO name;
    private UserImageGoogleDTO image;
    private boolean isPlusUser;
    private String language;
    private boolean verified;
}
