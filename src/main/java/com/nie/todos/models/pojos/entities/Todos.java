package com.nie.todos.models.pojos.entities;

import com.nie.todos.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * Created by inna on 21.05.17.
 */
@Data
@AllArgsConstructor
public class Todos {
    private UUID uniqueid;
    private String title;
    private Status status;

    public void toggleStatus() {
        this.status = isComplete() ? Status.ACTIVE : Status.COMPLETE;
    }

    public boolean isComplete() {
        return this.status == Status.COMPLETE;
    }

}
