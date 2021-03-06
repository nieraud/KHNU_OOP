package com.nie.todos.models.pojos.entities;

import com.nie.todos.utils.status.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * Created by inna on 21.05.17.
 */
@Data
@AllArgsConstructor
public class Todos {
    private String title;
    private String id;
    private Status status;
    private Date dateadded;
    private String iduser;
}
