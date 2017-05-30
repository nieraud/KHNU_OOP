package com.nie.todos.models.pojos.dtos.userDTO.googleDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by inna on 29.05.17.
 */
@Data
@AllArgsConstructor
public class UserImageGoogleDTO {
    private String url;
    private boolean isDefault;
}
