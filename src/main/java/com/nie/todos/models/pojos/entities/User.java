package com.nie.todos.models.pojos.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by inna on 29.05.17.
 */
@Data
@AllArgsConstructor
public class User {
    private String id;
    private String fname;
    private String lname;
    private String imageurl;
    private String accesstoken;
    private Timestamp datereg;
}
